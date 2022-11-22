package com.tmp.spring_template.module.openapi;

import com.tmp.spring_template.module.openapi.model.Api;
import com.tmp.spring_template.module.openapi.model.ApplicationConfig;
import com.tmp.spring_template.module.openapi.model.Header;
import com.tmp.spring_template.module.openapi.model.SecurityHeader;
import com.tmp.spring_template.extension.openapi.AppHeader;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Configuration
@AllArgsConstructor
// TODO CHECK LATER
//@Profile({ AppEnvironment.DEVELOPMENT, AppEnvironment.UAT, AppEnvironment.PREP })
public class SwaggerConfig {

    public static final String DEFAULT_API_DESCRIPTION = "Uygulama Servisleri";
    private static final String HDR_PREFIX = "hdr";
    private static final String COMPONENTS_PREFIX = "#/components/parameters/" + HDR_PREFIX;
    private static final String API_IDENTIFIER_KEY = "x-api-id";

    @Autowired
    private ApplicationConfig applicationConfig;


    @Bean
    public OpenAPI customOpenAPI() {

        /**
         * Normal şartlar altında domains nullable olamaz.
         * Config server yml verisi çekilemediğinde ve hatalar oluştuğunda hatayı saptayabilmek için eklendi.
         * istenirse nullable olmayan haline döndürülebilir.
         */
        final List<Server> servers = Optional
                .ofNullable(applicationConfig.getDomains())
                .orElse(Collections.emptyList())
                .stream()
                .map(new Server()::url)
                .collect(Collectors.toList());

        /**
         * Henüz üzerinde işlem yapılmamış tüm API'ler
         */
        final List<Api> apiConfigList = applicationConfig
                .getApis()
                .values()
                .stream()
                .filter(api -> BooleanUtils.isNotTrue(api.getProcessApiFlag())).collect(Collectors.toList());

        /**
         * Bu Api için yml configürasyonu
         */
        final Api currentApiConfig = CollectionUtils.isNotEmpty(apiConfigList) ? apiConfigList.get(0) : null;

        /**
         * Ortak headerları ve api bazlı headerleri tek yerde topluyoruz ve topluca oluşturuyoruz.
         * Apilere ekleme işlemi bir sonraki adımda yapılacak.
         */
        Map<String, Header> headerMap = new HashMap<>();
        headerMap.putAll(applicationConfig.getCommonHeaders());

        if (Objects.nonNull(currentApiConfig) && MapUtils.isNotEmpty(currentApiConfig.getHeaders())) {
            headerMap.putAll(currentApiConfig.getHeaders());
        }

        final OpenAPI openAPI = new OpenAPI();
        openAPI
                .servers(servers)
                .components(createComponents(openAPI, headerMap))
                .info(new Info().title(applicationConfig.getName()).version(applicationConfig.getVersion()));

        if (Objects.nonNull(currentApiConfig)) {

            /**
             * API özelleştirmeleri için herhangi bir noktada bu API'nin hangi API olduğu anlamak için kullanılacak eşsiz tanımlayıcı.
             */
            setApiIdentifier(openAPI, currentApiConfig.getApiId());
            currentApiConfig.setProcessApiFlag(Boolean.TRUE);
        }

        /**
         * Authorize alanında gösterilecek headerların eklenmesi
         * Yml'ye yazılanlar setApiIdentifier() ile openApi üzerine eklendikten sonra çalışır.
         * Bu nedenle önce bu metod çalışmalı!
         */
        addDefaultSecurityHeaders(openAPI);
        addAllSecurityHeadersFromYml(openAPI);

        return openAPI;
    }

    /**
     * Uygulama koduna gömülecek default security header'ları varsa burada alınacaklar.
     * @param openAPI
     */
    private void addDefaultSecurityHeaders(OpenAPI openAPI) {

        final List<AppHeader> defaultAppSecurityHeaders = null;

        final Map<String, SecurityHeader> defaultSecurityHeaders = new HashMap<>();
        Optional.ofNullable(defaultAppSecurityHeaders).orElse(new ArrayList<>()).stream().forEach(appHeader -> {
            final SecurityHeader securityHeader = new SecurityHeader();
            securityHeader.setKey(appHeader.getKey());
            securityHeader.setName(appHeader.getName());
            securityHeader.setDescription(appHeader.getDescription());

            defaultSecurityHeaders.put(securityHeader.getKey(), securityHeader);
        });

        addSecurityHeaders(openAPI, defaultSecurityHeaders);
    }

    private void addAllSecurityHeadersFromYml(OpenAPI openAPI) {

        final Api currentApiConfig = getApiConfigByIdentifier(openAPI);

        Stream<Map.Entry<String, SecurityHeader>> securityHeaderStream = MapUtils
                .emptyIfNull(applicationConfig.getCommonSecurityHeaders())
                .entrySet()
                .stream();
        if (Objects.nonNull(currentApiConfig) && MapUtils.isNotEmpty(currentApiConfig.getSecurityHeaders())) {
            securityHeaderStream = Stream.concat(securityHeaderStream, currentApiConfig.getSecurityHeaders().entrySet().stream());
        }

        Map<String, SecurityHeader> securityHeaderMap = securityHeaderStream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        addSecurityHeaders(openAPI, securityHeaderMap);

    }

    private void addSecurityHeaders(OpenAPI openAPI, Map<String, SecurityHeader> securityHeaderMap) {

        final Components components = openAPI.getComponents();

        /**
         * Ek güvenlik header'larını Authorize alanında göstermek için gereken ilk adım
         */
        securityHeaderMap.forEach((securityHeaderKey, securityHeader) -> {
            components.addSecuritySchemes(securityHeader.getKey(), new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.HEADER)
                    .name(securityHeader.getKey())
                    .description(securityHeader.getDescription()));
        });

        /**
         * Güvenlik header'larını Authorize alanında göstermek için gereken ikinci adım
         */
        final SecurityRequirement securityRequirement = new SecurityRequirement();

        securityHeaderMap.forEach((securityHeaderKey, securityHeader) -> {
            securityRequirement.addList(securityHeader.getKey());
        });

        if (Objects.isNull(openAPI.getSecurity())) {
            final List<SecurityRequirement> list = new ArrayList<>();
            list.add(securityRequirement);
            openAPI.security(list);
        } else {
            openAPI.getSecurity().add(securityRequirement);
        }

    }

    private Components createComponents(OpenAPI openAPI, Map<String, Header> headers) {

        final Components components = new Components();

        /**
         * Burada yml dosyasında tanımlanan ortak headerlar ile apiye özel headerları oluşturuyoruz.
         */
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach((headerKey, header) -> {
                components.addParameters(HDR_PREFIX + header.getKey(), new HeaderParameter()
                        .required(header.getRequired())
                        .name(header.getKey())
                        .example(header.getExample())
                        .description(header.getDescription())
                        .schema(new StringSchema()));
            });
        }

        return components;
    }

    @Bean
    public OpenApiCustomiser customOpenAPIHeaderCustomiser() {

        //@formatter:off
        return openApi -> {

            final Api apiConfig = getApiConfigByIdentifier(openApi);
            final String apiUrlPrefix = apiConfig.getPath().replace("*", "");
            final List<PathItem> pathItemList = new ArrayList<>();

            /**
             * Prepare header - api map for API specific header assignment
             */
            openApi.getPaths().forEach((openApiPathItemUrl, pathItem) -> {
                if (openApiPathItemUrl.startsWith(apiUrlPrefix)) {
                    pathItemList.add(pathItem);
                }
            });

            /**
             * Add API specific headers to related apis
             */
            pathItemList.stream().forEach(pathItem -> addHeaderToPathItem(apiConfig.getHeaders(), pathItem));

            /**
             * Add common headers to all apis
             */
            final Map<String, Header> commonHeaders = applicationConfig.getCommonHeaders();
            openApi
                    .getPaths()
                    .values()
                    .stream()
                    .forEach(pathItem -> addHeaderToPathItem(commonHeaders, pathItem));

            openApi.getInfo().description(StringUtils.defaultString(Optional.ofNullable(apiConfig).map(Api::getDescription).orElse(DEFAULT_API_DESCRIPTION)));
        };
        //@formatter:on
    }

    private void addHeaderToPathItem(Map<String, Header> headerMap, PathItem pathItem) {

        pathItem.readOperations().stream().forEach(operation -> {
            if (MapUtils.isNotEmpty(headerMap)) {
                headerMap.forEach((headerKey, header) -> {
                    final String componentRef = COMPONENTS_PREFIX + header.getKey();
                    /**
                     Bu header zaten varsa yoksayılacak. Var olan bir header tekrar eklenince duplike oluyor. Definition değiştirince hedaerlar çoklandığı için bunu yapıyoruz.
                     */
                    if (Optional.ofNullable(operation.getParameters()).orElse(new ArrayList<>()).stream().anyMatch(op -> StringUtils.equals(header.getKey(), op.getName()))) {
                        return;
                    }

                    /**
                     * Yeni header eklenecek.
                     */
                    operation.addParametersItem(new HeaderParameter().$ref(componentRef).name(header.getKey()).description(header.getDescription()).example(header.getExample()).required(header.getRequired()));
                });
            }
        });
    }

    private void setApiIdentifier(OpenAPI openAPI, String apiIdentifier) {

        Map<String, Object> extensions = new HashMap<>();
        extensions.put(API_IDENTIFIER_KEY, apiIdentifier);
        openAPI.setExtensions(extensions);
    }

    private String getApiIdentifier(OpenAPI openAPI) {

        final Map<String, Object> extensions = openAPI.getExtensions();
        if (MapUtils.isNotEmpty(extensions)) {
            return (String) extensions.get(API_IDENTIFIER_KEY);
        }
        return null;
    }

    private Api getApiConfigByIdentifier(OpenAPI openAPI) {

        final String apiIdentifier = getApiIdentifier(openAPI);
        return getApiConfigByIdentifier(apiIdentifier);
    }

    private Api getApiConfigByIdentifier(String apiIdentifier) {

        if (StringUtils.isBlank(apiIdentifier)) {
            return null;
        }
        return applicationConfig.getApis().values().stream().filter(api -> api.getApiId().equals(apiIdentifier)).findFirst().orElse(null);
    }

}
