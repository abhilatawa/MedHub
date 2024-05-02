package org.asdc.medhub.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS policy configuration class
 */
@Configuration
public class CorsConfig {

    /**
     * Custom configuration object
     */
    private final CustomConfigurations customConfigurations;

    /**
     * Parameterized constructor
     * @param customConfigurations custom configuration object
     */
    @Autowired
    public CorsConfig(CustomConfigurations customConfigurations){
        this.customConfigurations=customConfigurations;
    }

    /**
     * CORS policy configurer bean
     * @return WebMvcConfigurer object
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(customConfigurations.frontEndAppUrl)
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}