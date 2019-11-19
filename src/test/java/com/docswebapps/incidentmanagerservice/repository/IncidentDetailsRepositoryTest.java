package com.docswebapps.incidentmanagerservice.repository;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class IncidentDetailsRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    IncidentDetailsRepository incidentDetailsRepository;

    @Autowired
    ServiceDetailsRepository serviceDetailsRepository;

    ServiceDetails serviceDetails = ServiceDetails.builder()
            .serviceName(ServiceName.PRODUCTION)
            .status(ServiceStatus.GREEN)
            .previousIncidentCount(0L)
            .lastIncidentDate(Timestamp.valueOf(LocalDateTime.now()))
            .build();

    IncidentDetails incidentDetails1 = IncidentDetails.builder()
            .serviceDetails(serviceDetails)
            .status(IncidentStatus.OPEN)
            .description("Test Incident 1")
            .severity(Severity.P2)
            .build();

    IncidentDetails incidentDetails2 = IncidentDetails.builder()
            .serviceDetails(serviceDetails)
            .status(IncidentStatus.CLOSED)
            .closedDate(Timestamp.valueOf(LocalDateTime.now()))
            .description("Test Incident 2")
            .severity(Severity.P2)
            .build();

    IncidentDetails incidentDetails3 = IncidentDetails.builder()
            .serviceDetails(serviceDetails)
            .status(IncidentStatus.OPEN)
            .description("Test Incident 3")
            .severity(Severity.P1)
            .build();

    @BeforeEach
    void setUpDBData() {
        testEntityManager.persist(serviceDetails);
        testEntityManager.persist(incidentDetails1);
        testEntityManager.persist(incidentDetails2);
        testEntityManager.persist(incidentDetails3);
        testEntityManager.flush();
    }

    @Test
    @DisplayName("IncidentDetailsRepositoryTest: testCountByStatusAndServiceDetails()")
    void testCountByStatusAndServiceDetails() {
        Optional<ServiceDetails> serviceDetailsFromDB = serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString());
        serviceDetailsFromDB.ifPresent(details -> assertAll("Check Incident Counts",
                () -> assertEquals(2, incidentDetailsRepository.countByStatusAndServiceDetails(IncidentStatus.OPEN.toString(), serviceDetails), "IncidentDetailsRepositoryTest: open counts don't match"),
                () -> assertEquals(1, incidentDetailsRepository.countByStatusAndServiceDetails(IncidentStatus.CLOSED.toString(), serviceDetails), "IncidentDetailsRepositoryTest: closed counts don't match")));
    }

    @Test
    @DisplayName("IncidentDetailsRepositoryTest: testCountBySeverityAndStatusAndServiceDetails()")
    void testCountBySeverityAndStatusAndServiceDetails() {
        Optional<ServiceDetails> serviceDetailsFromDB = serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString());
        serviceDetailsFromDB.ifPresent(details -> assertAll("Check Incident Counts",
                () -> assertEquals(1, incidentDetailsRepository
                        .countBySeverityAndStatusAndServiceDetails(Severity.P2.toString(), IncidentStatus.OPEN.toString(), serviceDetails),
                        "IncidentDetailsRepositoryTest: P2 open counts don't match"),
                () -> assertEquals(1, incidentDetailsRepository
                        .countBySeverityAndStatusAndServiceDetails(Severity.P1.toString(),IncidentStatus.OPEN.toString(), serviceDetails),
                        "IncidentDetailsRepositoryTest: P1 open counts don't match"),
                () -> assertEquals(0, incidentDetailsRepository
                                .countBySeverityAndStatusAndServiceDetails(Severity.P3.toString(),IncidentStatus.OPEN.toString(), serviceDetails),
                        "IncidentDetailsRepositoryTest: P3 open counts don't match"),
                () -> assertEquals(1, incidentDetailsRepository
                                .countBySeverityAndStatusAndServiceDetails(Severity.P2.toString(),IncidentStatus.CLOSED.toString(), serviceDetails),
                        "IncidentDetailsRepositoryTest: P2 closed counts don't match")
        ));
    }
    @Test
    @DisplayName("IncidentDetailsRepositoryTest: testFindAllByServiceDetails()")
    void testFindAllByServiceDetails() {
        Optional<ServiceDetails> serviceDetailsFromDB = serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString());
        serviceDetailsFromDB.ifPresent(details -> assertAll("Check Incident Counts",
                () -> assertEquals(3, incidentDetailsRepository.findAllByServiceDetails(serviceDetails).size(), "IncidentDetailsRepositoryTest: List<IncidentDetails> counts don't match")
        ));
    }


}
