package com.shilko.ru.wither.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationConfigController {

    private static ApplicationContext applicationContext;

    static {
        applicationContext = new ClassPathXmlApplicationContext("ApplicationConfig.xml");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
