package com.lmp.geographyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.lmp.geographyservice",
        "com.lmp.flightbookingcommon"
})
public class GeographyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeographyServiceApplication.class, args);
    }

}
