package com.tmp.spring_template.demo.rest_api.test_api;

import com.tmp.spring_template.demo.TestApiConstants;
import com.tmp.spring_template.demo.database.jpa_repository.DemoRepository;
import com.tmp.spring_template.demo.rest_api.test_api.model.DemoRequest;
import com.tmp.spring_template.demo.rest_api.test_api.model.DemoResponse;
import com.tmp.spring_template.demo.rest_api.test_api.model.QueryDemoRequest;
import com.tmp.spring_template.demo.rest_api.test_api.model.QueryDemoResponse;
import com.tmp.spring_template.demo.service.demo_service.DemoDatabaseService;
import com.tmp.spring_template.demo.service.demo_service.model.Demo;
import com.tmp.spring_template.demo.service.demo_service.model.QueryDemoInput;
import com.tmp.spring_template.demo.service.demo_service.model.QueryDemoOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * TODO CLEAN: TEST
 */
@AllArgsConstructor
@RestController
@RequestMapping(value = {TestApiConstants.API_PATH_TEST_V1 + "/database"})
public class DemoDatabaseController {

    private final String controllerTag = "Test API Database Services";

    @Autowired
    private DemoRepository demoRepository;

    @Autowired
    private DemoDatabaseService demoDatabaseService;

    //@formatter:off
    @Operation(
            tags = controllerTag,
            summary = "Database query test",
            description = "Database query test description"
    )
    //@formatter:on
    @GetMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> queryService() {

        final Map<String, Object> response = new HashMap<>();

        response.put("DemoEntity findFirstByTextAndFlag(String text, boolean flag)", demoRepository.findFirstByTextAndFlag("Java", true));
        response.put("Optional<DemoEntity> findFirstByText(String text)", demoRepository.findFirstByText("Java"));
        response.put("findAll()", demoRepository.findAll());
        response.put("findAll() | Sorted by NumValue", demoRepository.findAll(Sort.by(Sort.Direction.ASC, "numValue")));
        response.put("List<DemoEntity> findAllByFlag(boolean flag)", demoRepository.findAllByFlag(true));
        response.put("SELECT D FROM DemoEntity D WHERE D.text = :textFilter", demoRepository.getEntities("Java"));
        response.put("SELECT D FROM demo_table D WHERE D.TEXT = :textFilter AND D.FLAG = :flagFilter | NATIVE = TRUE", demoRepository.getEntities("Java", true));

        return ResponseEntity.ok(response);
    }

    @Operation(
            tags = controllerTag,
            summary = "Database select test",
            description = "Database select test description",
            responses = {
                    @ApiResponse(description = "Successful Result", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = QueryDemoResponse.class))),
                    @ApiResponse(description = "Not Found", responseCode = "204", content = @Content)
            }
    )
    //@formatter:on
    @GetMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryDemoResponse> demoSelect(@Validated @ParameterObject QueryDemoRequest request) {

        final QueryDemoInput input = TestApiMapper.INSTANCE.convertToQueryDemoInput(request);
        final QueryDemoOutput output = demoDatabaseService.getDemoEntities(input);

        if (Objects.isNull(output) || CollectionUtils.isEmpty(output.getResult())) {
            return ResponseEntity.noContent().build();
        }

        final QueryDemoResponse response = TestApiMapper.INSTANCE.convertQueryDemoResponse(output);

        return ResponseEntity.ok(response);
    }

    @Operation(
            tags = controllerTag,
            summary = "Database get specific record test",
            description = "Database get specific record test description",
            responses = {
                    @ApiResponse(description = "Successful Result", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DemoResponse.class))),
                    @ApiResponse(description = "Not Found", responseCode = "204", content = @Content)
            }
    )
    //@formatter:on
    @GetMapping(value = "/demo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DemoResponse> demoSelectItem(@PathVariable("id") Long id) {

        final Demo dto = demoDatabaseService.getDemoEntity(id);

        if (Objects.isNull(dto)) {
            return ResponseEntity.noContent().build();
        }

        final DemoResponse response = TestApiMapper.INSTANCE.convertToDemoResponse(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            tags = controllerTag,
            summary = "Database insert test",
            description = "Database insert test description",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Example #1",
                                            summary = "Example #1 Summary",
                                            description = "Example #1 Description",
                                            value = "{\"text\":\"Demo Text\",\"flag\":false,\"numValue\":56,\"dateValue\":\"2022-11-04\",\"datetimeValue\":\"2022-11-04T22:58:29\"}"
                                    ),
                                    @ExampleObject(
                                            name = "Example #2",
                                            summary = "Example #2 Summary",
                                            description = "Example #2 Description",
                                            value = "{\"text\":\"Demo Lang X\",\"flag\":true,\"numValue\":23,\"dateValue\":\"2021-11-04\",\"datetimeValue\":\"2021-11-04T22:58:29\"}"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(description = "Successful Result", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DemoResponse.class)))
            }
    )
    //@formatter:on
    @PutMapping(value = "/demo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DemoResponse> demoInsert(@Validated @RequestBody DemoRequest request) {

        Demo dto = TestApiMapper.INSTANCE.convertToDemo(request);
        dto = demoDatabaseService.insertDemo(dto);
        final DemoResponse response = TestApiMapper.INSTANCE.convertToDemoResponse(dto);

        return ResponseEntity.ok(response);
    }


}
