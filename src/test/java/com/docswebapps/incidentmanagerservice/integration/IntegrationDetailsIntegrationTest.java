package com.docswebapps.incidentmanagerservice.integration;

import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import com.docswebapps.incidentmanagerservice.repository.IncidentDetailsRepository;
import com.docswebapps.incidentmanagerservice.repository.ServiceDetailsRepository;
import com.docswebapps.incidentmanagerservice.service.IncidentDetailsService;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IntegrationDetailsIntegrationTest {
    @Mock
    IncidentDetailsRepository incidentDetailsRepository;

    @Mock
    ServiceDetailsRepository serviceDetailsRepository;

    @InjectMocks
    IncidentDetailsService incidentDetailsService;

    IncidentDetailsDto incidentDetailsDto = IncidentDetailsDto.builder()
            .description("Test Incident")
            .severity(Severity.P2)
            .serviceName(ServiceName.PRODUCTION)
            .build();


    @Test
    void createIncident() {

    }
}
