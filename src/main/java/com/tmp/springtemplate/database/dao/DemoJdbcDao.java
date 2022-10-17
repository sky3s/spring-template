package com.tmp.springtemplate.database.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DemoJdbcDao extends BaseJdbcDao {



    public DemoJdbcDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }



}
