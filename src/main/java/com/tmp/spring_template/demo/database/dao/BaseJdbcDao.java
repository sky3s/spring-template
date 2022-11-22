package com.tmp.spring_template.demo.database.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@AllArgsConstructor
public abstract class BaseJdbcDao {

    private NamedParameterJdbcTemplate jdbcTemplate;



}
