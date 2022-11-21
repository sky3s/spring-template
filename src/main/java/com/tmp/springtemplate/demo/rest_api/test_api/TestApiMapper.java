package com.tmp.springtemplate.demo.rest_api.test_api;

import com.tmp.springtemplate.demo.rest_api.test_api.model.DemoRequest;
import com.tmp.springtemplate.demo.rest_api.test_api.model.DemoResponse;
import com.tmp.springtemplate.demo.rest_api.test_api.model.QueryDemoRequest;
import com.tmp.springtemplate.demo.rest_api.test_api.model.QueryDemoResponse;
import com.tmp.springtemplate.demo.service.demo_service.model.Demo;
import com.tmp.springtemplate.demo.service.demo_service.model.QueryDemoInput;
import com.tmp.springtemplate.demo.service.demo_service.model.QueryDemoOutput;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created on November 2022
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestApiMapper {

    TestApiMapper INSTANCE = Mappers.getMapper(TestApiMapper.class);


    QueryDemoInput convertToQueryDemoInput(QueryDemoRequest request);

    QueryDemoResponse convertQueryDemoResponse(QueryDemoOutput output);

    List<DemoResponse> convertToDemoResponse(List<Demo> objectList);

    DemoResponse convertToDemoResponse(Demo dto);

    Demo convertToDemo(DemoRequest request);


}
