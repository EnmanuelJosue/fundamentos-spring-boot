package com.fundamentos.springboot.fundamentos.bean;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class MyBeanImplement implements MyBean {
    Log LOGGER = LogFactory.getLog(MyBeanWithDependencyImplement.class);

    @Override
    public void print() {
        LOGGER.info("Hemos ingresado al metodo MyBeanImplement propia");

        System.out.println("Hola desde mi implementacion propia");
    }
}
