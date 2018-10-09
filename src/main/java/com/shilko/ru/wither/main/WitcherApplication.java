package com.shilko.ru.wither.main;

import org.springframework.context.*;
import com.shilko.ru.wither.config.ApplicationConfigController;

/*import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication*/
public class WitcherApplication {

    /*public static void main(String[] args) {
        SpringApplication.run(WitcherApplication.class, args);
    }*/
    public static void main(String[] args) {
        ApplicationContext applicationContext = ApplicationConfigController.getApplicationContext();
        Car car = applicationContext.getBean("car", Car.class);
        System.out.println(car.getS());
    }
}
