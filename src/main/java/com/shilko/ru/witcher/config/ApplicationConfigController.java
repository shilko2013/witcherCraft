package com.shilko.ru.witcher.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The type Application config controller for getting ApplicationContext.
 */
public class ApplicationConfigController {

    private static ApplicationContext applicationContext;

    static {
        applicationContext = new ClassPathXmlApplicationContext("src/main/WEB-INF/ApplicationConfig.xml");
    }

    /**
     * Gets application context.
     *
     * @return the application context
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
