package com.docswebapps.incidentmanagerservice.config;

import com.docswebapps.incidentmanagerservice.util.ServiceMessageDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilBeansConfig {
    @Bean
    public ServiceMessageDetails serviceMessageDetails() {
        return new ServiceMessageDetails();
    }
}
