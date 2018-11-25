package com.shilko.ru.witcher.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The type Application config controller for getting ApplicationContext.
 */
public class ApplicationConfigController {

    private final static ApplicationContext applicationContext;

    private final static long MAX_FILE_SIZE = 0xA00000; //10Mb

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

    public static  long getMaxFileSize() {return MAX_FILE_SIZE;}
}
