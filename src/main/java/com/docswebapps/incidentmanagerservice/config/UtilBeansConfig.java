package com.docswebapps.incidentmanagerservice.config;

import com.docswebapps.incidentmanagerservice.util.ServiceMessageDetails;
import com.docswebapps.incidentmanagerservice.util.UpdateServiceDetailsStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;

@Configuration
public class UtilBeansConfig {
    @Bean
    public ServiceMessageDetails serviceMessageDetails() {
        return new ServiceMessageDetails();
    }

    @Bean
    public UpdateServiceDetailsStatus updateServiceDetailsStatus() { return new UpdateServiceDetailsStatus(); }

    // Add ETags to HTTP response headers
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
