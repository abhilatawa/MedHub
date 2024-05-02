package org.asdc.medhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Spring boot application main class
 */
@EnableAsync
@SpringBootApplication()
public class AsdcApplication {

    /**
     * Spring application main method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AsdcApplication.class, args);
    }

}
