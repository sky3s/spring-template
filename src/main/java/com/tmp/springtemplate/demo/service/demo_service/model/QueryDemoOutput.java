package com.tmp.springtemplate.demo.service.demo_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created on November, 2022
 */
@Getter
@Setter
@NoArgsConstructor
public class QueryDemoOutput {

    private List<Demo> result;

}
