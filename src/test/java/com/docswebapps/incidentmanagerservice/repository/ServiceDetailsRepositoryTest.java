package com.docswebapps.incidentmanagerservice.repository;

import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ServiceDetailsRepositoryTest extends BaseDataJpaTest {

    @Test
    @DisplayName("testFindByServiceName()")
    void testFindByServiceName() {
        Optional<ServiceDetails> serviceDetailsFromDB = serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString());
        serviceDetailsFromDB.ifPresent(details -> assertAll("Check Service Details",
                () -> assertEquals(serviceList.get(0).getServiceName(), details.getServiceName(), "ServiceDetailsRepositoryTest: serviceNames don't match"),
                () -> assertEquals(serviceList.get(0).getStatus(), details.getStatus(),"ServiceDetailsRepositoryTest: statuses don't match"),
                () -> assertEquals(serviceList.get(0).getPreviousIncidentCount(), details.getPreviousIncidentCount(),"ServiceDetailsRepositoryTest: previousIncidentCounts don't match"),
                () -> assertEquals(serviceList.get(0).getLastIncidentDate(), details.getLastIncidentDate(),"ServiceDetailsRepositoryTest: lastIncidentDate don't match")));
    }

}
