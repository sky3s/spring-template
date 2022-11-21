package com.tmp.springtemplate.demo.service.demo_service;

import com.tmp.springtemplate.demo.database.entity.DemoEntity;
import com.tmp.springtemplate.demo.service.demo_service.model.Demo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created on November, 2022
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DemoServiceMapper {

    DemoServiceMapper INSTANCE = Mappers.getMapper(DemoServiceMapper.class);


    List<Demo> convertToDemo(List<DemoEntity> entityList);

    Demo convertToDemo(DemoEntity entity);

    DemoEntity convertToDemoEntity(Demo dto);

}
