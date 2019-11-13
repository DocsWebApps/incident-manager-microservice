package com.docswebapps.incidentmanagerservice.util;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import com.docswebapps.incidentmanagerservice.repository.IncidentDetailsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceMessageDetailsTest {
    @Mock
    private IncidentDetailsRepository incidentDetailsRepository;

    @InjectMocks
    ServiceMessageDetails serviceMessageDetails;

    private ServiceDetails serviceDetails = ServiceDetails.builder()
            .id(1L)
            .serviceName(ServiceName.PRODUCTION)
            .lastIncidentDate(null)
            .previousIncidentCount(5L)
            .version(0L)
            .status(ServiceStatus.GREEN)
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
            .build();


    private IncidentDetails incidentDetails = IncidentDetails.builder()
            .id(1L)
            .version(0L)
            .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .description("Test Incident")
            .closedDate(Timestamp.valueOf(LocalDateTime.now()))
            .severity(Severity.P2)
            .status(IncidentStatus.CLOSED)
            .serviceDetails(serviceDetails)
            .build();

    private List<String> messageDetails;

    @Test
    @DisplayName("Incident occurred in the last 24 hours")
    void incidentInLast24() {
        when(incidentDetailsRepository.countByStatusAndServiceDetails(any(String.class), any(ServiceDetails.class)))
        .thenReturn(1);

        messageDetails = serviceMessageDetails.getMessageDetails(serviceDetails);
        assertEquals("1 incidents(s) in the last 24 hours", messageDetails.get(0));
        assertEquals("system-red", messageDetails.get(1));
    }

    @Test
    @DisplayName("No previous incidents recorded")
    void noPreviousIncidents() {
        when(incidentDetailsRepository.countByStatusAndServiceDetails(any(String.class), any(ServiceDetails.class)))
                .thenReturn(0);

        messageDetails = serviceMessageDetails.getMessageDetails(serviceDetails);
        assertEquals("No previous incidents recorded", messageDetails.get(0));
        assertEquals("system-green", messageDetails.get(1));
    }

    @Test
    @DisplayName("Number of days since last incident")
    void daysSinceLastIncident() {
        when(incidentDetailsRepository.countByStatusAndServiceDetails(any(String.class), any(ServiceDetails.class)))
                .thenReturn(0);

        serviceDetails.setLastIncidentDate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
        messageDetails = serviceMessageDetails.getMessageDetails(serviceDetails);
        assertEquals("5 Day(s) since last incident", messageDetails.get(0));
        assertEquals("system-green", messageDetails.get(1));
    }

}
