package com.tmp.springtemplate.rest_api.test_api;

import com.tmp.springtemplate.database.jpa_repository.DemoRepository;
import com.tmp.springtemplate.rest_api.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO CLEAN: TEST
 */
@AllArgsConstructor
@RestController
@RequestMapping(value = {ApiConstants.API_PATH_TEST_V1 + "/database"})
public class DatabaseTestController {

    private final String controllerTag = "Test API Database Services";

    private final DemoRepository demoRepository;

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

}
