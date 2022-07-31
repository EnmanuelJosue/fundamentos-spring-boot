package com.fundamentos.springboot.fundamentos.bean;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class MyBean2Implement implements MyBean {
    Log LOGGER = LogFactory.getLog(MyBeanWithDependencyImplement.class);

    @Override
    public void print() {
        LOGGER.info("Hemos ingresado al metodo MyBean2Implement");
        System.out.println("Hola desde mi implementacion 2 propia bean2");
    }
}
