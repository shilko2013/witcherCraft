package com.shilko.ru.witcher.main;

import org.springframework.context.*;
import com.shilko.ru.witcher.config.ApplicationConfigController;

/*import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication*/
public class WitcherApplication {

    /*public static void main(String[] args) {
        SpringApplication.run(WitcherApplication.class, args);
    }*/
    public static void main(String[] args) {
        ApplicationContext applicationContext = ApplicationConfigController.getApplicationContext();
    }

}