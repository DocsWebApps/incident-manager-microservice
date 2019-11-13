package com.docswebapps.incidentmanagerservice.config;

import com.docswebapps.incidentmanagerservice.util.ServiceMessageDetails;
import com.docswebapps.incidentmanagerservice.util.UpdateServiceDetailsStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilBeansConfig {
    @Bean
    public ServiceMessageDetails serviceMessageDetails() {
        return new ServiceMessageDetails();
    }

    @Bean
    public UpdateServiceDetailsStatus updateServiceDetailsStatus() { return new UpdateServiceDetailsStatus(); }
}
