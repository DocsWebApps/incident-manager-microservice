package com.docswebapps.incidentmanagerservice.service.impl;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import com.docswebapps.incidentmanagerservice.repository.IncidentDetailsRepository;
import com.docswebapps.incidentmanagerservice.repository.ServiceDetailsRepository;
import com.docswebapps.incidentmanagerservice.util.UpdateServiceDetailsStatus;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncidentDetailsServiceImplTest {
    @Mock
    ServiceDetailsRepository serviceDetailsRepository;

    @Mock
    IncidentDetailsRepository incidentDetailsRepository;

    @Mock
    UpdateServiceDetailsStatus updateServiceDetailsStatus;

    @InjectMocks
    IncidentDetailsServiceImpl incidentDetailsService;

    private ServiceDetails serviceDetails = ServiceDetails.builder()
            .id(1L)
            .serviceName(ServiceName.PRODUCTION)
            .previousIncidentCount(0L)
            .status(ServiceStatus.GREEN)
            .lastIncidentDate(null)
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
            .version(0L)
            .build();

    private IncidentDetailsDto incidentDetailsDto = IncidentDetailsDto.builder()
            .description("Test Incident")
            .severity(Severity.P2)
            .serviceName(ServiceName.valueOf(serviceDetails.getServiceName()))
            .build();

    private IncidentDetails incidentDetails = IncidentDetails.builder()
            .id(1L)
            .status(IncidentStatus.OPEN)
            .severity(incidentDetailsDto.getSeverity())
            .description(incidentDetailsDto.getDescription())
            .serviceDetails(serviceDetails)
            .closedDate(null)
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
            .version(0L)
            .build();

    @Test
    @DisplayName("IncidentDetailsServiceImplTest: save() Happy Path")
    void saveIncidentHappyPath() {
        when(serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString())).thenReturn(Optional.of(serviceDetails));
        when(incidentDetailsRepository.save(any(IncidentDetails.class))).thenReturn(incidentDetails);
        assertEquals(1L, incidentDetailsService.saveIncident(incidentDetailsDto), "IncidentDetailsServiceImplTest.saveIncidentHappyPath(): return values don't match");
    }

    @Test
    @DisplayName("IncidentDetailsServiceImplTest: save() Sad Path :-(")
    void saveIncidentSadPath() {
        when(serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString())).thenReturn(Optional.empty());
        assertEquals(-1L, incidentDetailsService.saveIncident(incidentDetailsDto), "IncidentDetailsServiceImplTest.saveIncidentSadPath(): return values don't match");
    }
}
