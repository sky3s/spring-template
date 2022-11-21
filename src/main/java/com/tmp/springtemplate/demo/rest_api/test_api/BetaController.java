package com.tmp.springtemplate.demo.rest_api.test_api;

import com.tmp.springtemplate.extension.messaging.MessageHelper;
import com.tmp.springtemplate.extension.messaging.message_key.ExceptionMessageKey;
import com.tmp.springtemplate.extension.messaging.message_key.ValidationMessageKey;
import com.tmp.springtemplate.extension.openapi.AppHeader;
import com.tmp.springtemplate.extension.request.RequestUtils;
import com.tmp.springtemplate.module.message.MessageUtils;
import com.tmp.springtemplate.module.message.exception.ExceptionHelper;
import com.tmp.springtemplate.demo.TestApiConstants;
import com.tmp.springtemplate.demo.rest_api.test_api.model.RequestModel;
import com.tmp.springtemplate.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO CLEAN: TEST
 */
@AllArgsConstructor
@RestController
@RequestMapping(value = {TestApiConstants.API_PATH_TEST_V1 + "/beta"})
public class BetaController {

    private final String controllerTag = "Beta App Services";


    //@formatter:off
    @Operation(
            tags = controllerTag,
            summary = "Test Summary",
            description = "Test description"
    )
    //@formatter:on
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> betaService(
            @RequestHeader(value = "X-DemoApiParamHeader") String paramHeader
    ) {

        final Map<String, Object> response = new HashMap<>();
        response.put("boolField", true);
        response.put("strField", "demoField");
        response.put("messageTR", MessageHelper.getMessage(ValidationMessageKey.DEMO_VALIDATION, AppConstants.TURKISH_LOCALE, "Param 1", "Param 2"));
        response.put("messageEN", MessageHelper.getMessage(ValidationMessageKey.DEMO_VALIDATION, AppConstants.ENGLISH_LOCALE, "Param 1", "Param 2"));

        return ResponseEntity.ok(response);
    }

    //@formatter:off
    @Operation(
            tags = controllerTag,
            summary = "Test Theta Summary",
            description = "Test Theta description"
    )
    //@formatter:on
    @PostMapping(value = "/thd", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> thetaService(@Validated @RequestBody RequestModel request) {

        final Map<String, Object> response = new HashMap<>();
        response.put("boolField", true);
        response.put("strField", "demoField");
        response.put("message", MessageHelper.getMessage(request.getKey(), "Param 1", "Param 2"));
        response.put("messageTR", MessageHelper.getMessage(ValidationMessageKey.DEMO_VALIDATION, AppConstants.TURKISH_LOCALE, "Param 1", "Param 2"));
        response.put("messageEN", MessageHelper.getMessage(ValidationMessageKey.DEMO_VALIDATION, AppConstants.ENGLISH_LOCALE, "Param 1", "Param 2"));

        return ResponseEntity.ok(response);
    }

    //@formatter:off
    @Operation(
            tags = controllerTag,
            summary = "Test Known Exception Summary",
            description = "Test Known Exception description"
    )
    //@formatter:on
    @GetMapping(value = "/known-ex", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> knownExService() {

        throw ExceptionHelper.newExternalServiceException(ExceptionMessageKey.DEMO_EXCEPTION.getCode(), "Param1", "Param2");
    }

    //@formatter:off
    @Operation(
            tags = controllerTag,
            summary = "Test Unknown Exception Summary",
            description = "Test Unknown Exception description"
    )
    //@formatter:on
    @GetMapping(value = "/unknown-ex", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> unknownExService() {

        throw new NullPointerException("demo exception");
    }

    //@formatter:off
    @Operation(
            tags = controllerTag,
            summary = "Test Locale Summary",
            description = "Test Test Locale description"
    )
    //@formatter:on
    @GetMapping(value = "/lcl", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> localeService() {

        final Map<String, Object> response = new HashMap<>();
        response.put("boolField", true);
        response.put("header", RequestUtils.getHeader(AppHeader.ACCEPT_LANGUAGE));
        response.put("locale", MessageUtils.getLocale());
        response.put("message", MessageHelper.getMessage(ValidationMessageKey.DEMO_VALIDATION, "Param 1", "Param 2"));
        response.put("messageTR", MessageHelper.getMessage(ValidationMessageKey.DEMO_VALIDATION, AppConstants.TURKISH_LOCALE, "Param 1", "Param 2"));
        response.put("messageEN", MessageHelper.getMessage(ValidationMessageKey.DEMO_VALIDATION, AppConstants.ENGLISH_LOCALE, "Param 1", "Param 2"));

        return ResponseEntity.ok(response);
    }

}


