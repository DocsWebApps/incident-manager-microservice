package com.docswebapps.incidentmanagerservice.bootstrap;

import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
import com.docswebapps.incidentmanagerservice.repository.ServiceDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ServiceDetailsBaseDataInsert implements CommandLineRunner {
    private final ServiceDetailsRepository serviceDetailsRepository;

    public ServiceDetailsBaseDataInsert(ServiceDetailsRepository serviceDetailsRepository) {
        this.serviceDetailsRepository = serviceDetailsRepository;
    }
    @Override
    public void run(String... args) {
        if(serviceDetailsRepository.count() == 0) {
            this.serviceDetailsRepository.save(ServiceDetails.builder()
                    .serviceName("Production")
                    .status(ServiceStatus.GREEN)
                    .previousIncidentCount(0L)
                    .build());

            this.serviceDetailsRepository.save(ServiceDetails.builder()
                    .serviceName("Pre-Production")
                    .status(ServiceStatus.GREEN)
                    .previousIncidentCount(0L)
                    .build());

            this.serviceDetailsRepository.save(ServiceDetails.builder()
                    .serviceName("Fix On Fail")
                    .status(ServiceStatus.GREEN)
                    .previousIncidentCount(0L)
                    .build());
        }
    }

}
