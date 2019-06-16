package com.poc.eureka.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public EurekaOperations eurekaOperations(){
        return new EurekaOperations();
    }
}
