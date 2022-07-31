package com.fundamentos.springboot.fundamentos.bean;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class MyBeanWithPropertiesImplement implements MyBeanWithProperties{
    Log LOGGER = LogFactory.getLog(MyBeanWithDependencyImplement.class);

    private String nombre;
    private String apellido;

    public MyBeanWithPropertiesImplement(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    @Override
    public String function() {
        LOGGER.info("Hemos ingresado al metodo MyBeanWithPropertiesImplement");

        return nombre + "-" + apellido;
    }
}
