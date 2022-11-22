package com.tmp.spring_template.demo.rest_api.app_api;

import com.tmp.spring_template.extension.messaging.MessageHelper;
import com.tmp.spring_template.extension.messaging.message_key.ValidationMessageKey;
import com.tmp.spring_template.rest_api.ApiConstants;
import com.tmp.spring_template.util.AppConstants;
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
@RequestMapping(value = {ApiConstants.API_PATH_APP_V1 + "/test"})
public class AppTestController {

    private static final String controllerTag = "App API Test Services";


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
        response.put("apiName", "App API");
        response.put("messageTR", MessageHelper.getMessage(ValidationMessageKey.DEMO_VALIDATION, AppConstants.TURKISH_LOCALE, "Param 1", "Param 2"));
        response.put("messageEN", MessageHelper.getMessage(ValidationMessageKey.DEMO_VALIDATION, AppConstants.ENGLISH_LOCALE, "Param 1", "Param 2"));

        return ResponseEntity.ok(response);
    }

}
