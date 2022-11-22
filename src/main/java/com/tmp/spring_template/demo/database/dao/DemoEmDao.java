package com.tmp.spring_template.demo.database.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DemoEmDao extends BaseEntityManagerDao {



    public DemoEmDao(EntityManager entityManager, EntityManagerFactory entityManagerFactory) {
        super(entityManager, entityManagerFactory);
    }


}
