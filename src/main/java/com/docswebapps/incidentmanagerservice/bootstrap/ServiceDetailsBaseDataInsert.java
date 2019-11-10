package com.docswebapps.incidentmanagerservice.bootstrap;

import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
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
                    .serviceName(ServiceName.PRODUCTION)
                    .status(ServiceStatus.GREEN)
                    .previousIncidentCount(0L)
                    .build());

            this.serviceDetailsRepository.save(ServiceDetails.builder()
                    .serviceName(ServiceName.PRE_PRODUCTION)
                    .status(ServiceStatus.GREEN)
                    .previousIncidentCount(0L)
                    .build());

            this.serviceDetailsRepository.save(ServiceDetails.builder()
                    .serviceName(ServiceName.FIX_ON_FAIL)
                    .status(ServiceStatus.GREEN)
                    .previousIncidentCount(0L)
                    .build());
        }
    }

}
