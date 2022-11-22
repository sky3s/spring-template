package com.tmp.spring_template.demo.service.demo_service;

import com.tmp.spring_template.demo.database.entity.DemoEntity;
import com.tmp.spring_template.demo.database.jpa_repository.DemoRepository;
import com.tmp.spring_template.demo.service.demo_service.model.Demo;
import com.tmp.spring_template.demo.service.demo_service.model.QueryDemoInput;
import com.tmp.spring_template.demo.service.demo_service.model.QueryDemoOutput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created on November 2022
 */
@Slf4j
@Service
@AllArgsConstructor
public class DemoDatabaseService {

    private final DemoRepository demoRepository;


    public Demo getDemoEntity(Long id) {

        final Optional<DemoEntity> entity = demoRepository.findById(id);
        final Demo dto = DemoServiceMapper.INSTANCE.convertToDemo(entity.orElse(null));
        return dto;
    }

    public QueryDemoOutput getDemoEntities(QueryDemoInput input) {

        List<DemoEntity> demoEntityList = null;

        if (Objects.isNull(input)) {
            demoEntityList = demoRepository.findAll();
        } else if (BooleanUtils.isTrue(input.getUseNativeQuery())) {
            demoEntityList = demoRepository.getEntitiesNative(input.getFlag());
        } else {
            demoEntityList = demoRepository.findAllByFlag(BooleanUtils.isTrue(input.getFlag()));
        }

        final List<Demo> demoList = DemoServiceMapper.INSTANCE.convertToDemo(demoEntityList);

        final QueryDemoOutput output = new QueryDemoOutput();
        output.setResult(demoList);

        return output;
    }

    public Demo insertDemo(Demo dto) {

        final DemoEntity entity = DemoServiceMapper.INSTANCE.convertToDemoEntity(dto);
        demoRepository.save(entity);
        final Demo dtoResult = DemoServiceMapper.INSTANCE.convertToDemo(entity);

        return dtoResult;
    }

}
