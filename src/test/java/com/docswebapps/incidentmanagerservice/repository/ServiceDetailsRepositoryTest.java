package com.docswebapps.incidentmanagerservice.repository;

import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
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
class ServiceDetailsRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ServiceDetailsRepository serviceDetailsRepository;

    private ServiceDetails serviceDetails = ServiceDetails.builder()
            .serviceName(ServiceName.PRODUCTION)
            .status(ServiceStatus.GREEN)
            .previousIncidentCount(0L)
            .lastIncidentDate(Timestamp.valueOf(LocalDateTime.now()))
            .build();

    @Test
    @DisplayName("ServiceDetailsRepositoryTest:test findByServiceName()")
    void testFindByServiceName() {
        entityManager.persist(serviceDetails);
        entityManager.flush();

        Optional<ServiceDetails> serviceDetailsFromDB = serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString());
        serviceDetailsFromDB.ifPresent(details -> assertAll("Check Service Details",
                () -> assertEquals(serviceDetails.getServiceName(), details.getServiceName(), "ServiceDetailsRepositoryTest: serviceNames don't match"),
                () -> assertEquals(serviceDetails.getStatus(), details.getStatus(),"ServiceDetailsRepositoryTest: statuses don't match"),
                () -> assertEquals(serviceDetails.getPreviousIncidentCount(), details.getPreviousIncidentCount(),"ServiceDetailsRepositoryTest: previousIncidentCounts don't match"),
                () -> assertEquals(serviceDetails.getLastIncidentDate(), details.getLastIncidentDate(),"ServiceDetailsRepositoryTest: lastIncidentDate don't match")));
    }

}
