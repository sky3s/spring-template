package com.tmp.springtemplate.demo.database.dao;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@AllArgsConstructor
public abstract class BaseEntityManagerDao {

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected EntityManagerFactory entityManagerFactory;




}
