package com.docswebapps.incidentmanagerservice.bootstrap;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import com.docswebapps.incidentmanagerservice.repository.IncidentDetailsRepository;
import com.docswebapps.incidentmanagerservice.repository.ServiceDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(2)
public class IncidentDetailsBaseDataInsert implements CommandLineRunner {
    private final IncidentDetailsRepository incidentDetailsRepository;
    private final ServiceDetailsRepository serviceDetailsRepository;

    public IncidentDetailsBaseDataInsert(IncidentDetailsRepository incidentDetailsRepository,
                                         ServiceDetailsRepository serviceDetailsRepository) {
        this.incidentDetailsRepository = incidentDetailsRepository;
        this.serviceDetailsRepository = serviceDetailsRepository;
    }

    @Override
    public void run(String... args) {
        Optional<ServiceDetails> service = this.serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString());
        if(incidentDetailsRepository.count() == 0 && service.isPresent()) {
            this.incidentDetailsRepository.save(IncidentDetails.builder()
                    .severity(Severity.P2)
                    .status(IncidentStatus.OPEN)
                    .serviceDetails(service.get())
                    .description("Test Incident")
                    .build());
        }

    }
}
