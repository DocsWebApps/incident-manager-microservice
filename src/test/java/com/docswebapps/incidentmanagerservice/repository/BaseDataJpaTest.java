package com.docswebapps.incidentmanagerservice.repository;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class BaseDataJpaTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    IncidentDetailsRepository incidentDetailsRepository;

    @Autowired
    ServiceDetailsRepository serviceDetailsRepository;

    List<ServiceDetails> serviceList = Arrays.asList(
        ServiceDetails.builder()
            .serviceName(ServiceName.PRODUCTION)
            .status(ServiceStatus.GREEN)
            .previousIncidentCount(10L)
            .lastIncidentDate(Timestamp.valueOf(LocalDateTime.now()))
            .build(),
        ServiceDetails.builder()
            .serviceName(ServiceName.PRE_PRODUCTION)
            .status(ServiceStatus.RED)
            .previousIncidentCount(5L)
            .lastIncidentDate(Timestamp.valueOf(LocalDateTime.now()))
            .build(),
        ServiceDetails.builder()
            .serviceName(ServiceName.FIX_ON_FAIL)
            .status(ServiceStatus.AMBER)
            .previousIncidentCount(50L)
            .lastIncidentDate(Timestamp.valueOf(LocalDateTime.now()))
            .build()
    );

    List<IncidentDetails> incidentList = Arrays.asList(
        IncidentDetails.builder()
            .serviceDetails(serviceList.get(0))
            .status(IncidentStatus.OPEN)
            .description("Production Incident")
            .severity(Severity.P2)
            .build(),
        IncidentDetails.builder()
                .serviceDetails(serviceList.get(0))
                .status(IncidentStatus.CLOSED)
                .description("Production Incident")
                .severity(Severity.P3)
                .build(),
        IncidentDetails.builder()
            .serviceDetails(serviceList.get(1))
            .status(IncidentStatus.CLOSED)
            .closedDate(Timestamp.valueOf(LocalDateTime.now()))
            .description("Pre-Production Incident")
            .severity(Severity.P2)
            .build(),
        IncidentDetails.builder()
            .serviceDetails(serviceList.get(2))
            .status(IncidentStatus.OPEN)
            .description("Fix On Fail Incident")
            .severity(Severity.P1)
            .build()
    );

    @BeforeEach
    void setUpDBData() {
        serviceList.forEach((service)-> {
            testEntityManager.persist(service);
        });

        incidentList.forEach((incident) -> {
            testEntityManager.persist(incident);
        });
        testEntityManager.flush();
    }
}
