package com.tmp.springtemplate.rest_api.test_api;

import com.tmp.springtemplate.extension.messaging.MessageHelper;
import com.tmp.springtemplate.extension.messaging.message_key.ValidationMessageKey;
import com.tmp.springtemplate.rest_api.ApiConstants;
import com.tmp.springtemplate.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
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
@RequestMapping(value = {ApiConstants.API_PATH_TEST_V1 + "/test"})
public class TestApiTestController {

    private final String controllerTag = "Test API Test Services";


    //@formatter:off
    @Operation(
            tags = controllerTag,
            summary = "Test Summary",
            description = "Test description"
    )
    //@formatter:on
    @GetMapping(value = "/mtd", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> alphaService() {

        final Map<String, Object> response = new HashMap<>();
        response.put("boolField", true);
        response.put("strField", "demoField");
        response.put("apiName", "Test API");
        response.put("messageTR", MessageHelper.getMessage(ValidationMessageKey.DEMO_VALIDATION, AppConstants.TURKISH_LOCALE, "Param 1", "Param 2"));
        response.put("messageEN", MessageHelper.getMessage(ValidationMessageKey.DEMO_VALIDATION, AppConstants.ENGLISH_LOCALE, "Param 1", "Param 2"));

        return ResponseEntity.ok(response);
    }

}
