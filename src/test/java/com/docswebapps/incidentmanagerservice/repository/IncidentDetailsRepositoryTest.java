package com.docswebapps.incidentmanagerservice.repository;

import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class IncidentDetailsRepositoryTest extends BaseDataJpaTest {

    @Test
    @DisplayName("testCountByStatusAndServiceDetails()")
    void testCountByStatusAndServiceDetails() {
        Optional<ServiceDetails> serviceDetailsFromDB = serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString());
        serviceDetailsFromDB.ifPresent(details -> assertAll("Check Incident Counts",
                () -> assertEquals(1, incidentDetailsRepository.countByStatusAndServiceDetails(IncidentStatus.OPEN.toString(), serviceList.get(0)), "IncidentDetailsRepositoryTest: open counts don't match"),
                () -> assertEquals(1, incidentDetailsRepository.countByStatusAndServiceDetails(IncidentStatus.CLOSED.toString(), serviceList.get(0)), "IncidentDetailsRepositoryTest: closed counts don't match"),
                () -> assertEquals(0, incidentDetailsRepository.countByStatusAndServiceDetails(IncidentStatus.OPEN.toString(), serviceList.get(1)), "IncidentDetailsRepositoryTest: open counts don't match"),
                () -> assertEquals(1, incidentDetailsRepository.countByStatusAndServiceDetails(IncidentStatus.CLOSED.toString(), serviceList.get(1)), "IncidentDetailsRepositoryTest: closed counts don't match"),
                () -> assertEquals(1, incidentDetailsRepository.countByStatusAndServiceDetails(IncidentStatus.OPEN.toString(), serviceList.get(2)), "IncidentDetailsRepositoryTest: open counts don't match"),
                () -> assertEquals(0, incidentDetailsRepository.countByStatusAndServiceDetails(IncidentStatus.CLOSED.toString(), serviceList.get(2)), "IncidentDetailsRepositoryTest: closed counts don't match")
        ));
    }

    @Test
    @DisplayName("testCountBySeverityAndStatusAndServiceDetails()")
    void testCountBySeverityAndStatusAndServiceDetails() {
        Optional<ServiceDetails> serviceDetailsFromDB = serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString());
        serviceDetailsFromDB.ifPresent(details -> assertAll("Check Incident Counts",
                () -> assertEquals(1, incidentDetailsRepository
                        .countBySeverityAndStatusAndServiceDetails(Severity.P2.toString(), IncidentStatus.OPEN.toString(), serviceList.get(0)),
                        "IncidentDetailsRepositoryTest: PRODUCTION P2 open counts don't match"),
                () -> assertEquals(0, incidentDetailsRepository
                        .countBySeverityAndStatusAndServiceDetails(Severity.P1.toString(),IncidentStatus.OPEN.toString(), serviceList.get(0)),
                        "IncidentDetailsRepositoryTest: PRODUCTION P1 open counts don't match"),
                () -> assertEquals(1, incidentDetailsRepository
                                .countBySeverityAndStatusAndServiceDetails(Severity.P3.toString(),IncidentStatus.CLOSED.toString(), serviceList.get(0)),
                        "IncidentDetailsRepositoryTest: PRODUCTION P3 closed counts don't match"),
                () -> assertEquals(1, incidentDetailsRepository
                        .countBySeverityAndStatusAndServiceDetails(Severity.P2.toString(),IncidentStatus.CLOSED.toString(), serviceList.get(1)),
                        "IncidentDetailsRepositoryTest: PRE_PRODUCTION P2 closed counts don't match"),
                () -> assertEquals(0, incidentDetailsRepository
                        .countBySeverityAndStatusAndServiceDetails(Severity.P2.toString(),IncidentStatus.OPEN.toString(), serviceList.get(1)),
                        "IncidentDetailsRepositoryTest: PRE_PRODUCTION P2 open counts don't match"),
                () -> assertEquals(0, incidentDetailsRepository
                                .countBySeverityAndStatusAndServiceDetails(Severity.P3.toString(),IncidentStatus.OPEN.toString(), serviceList.get(2)),
                        "IncidentDetailsRepositoryTest: FIX_ON_FAIL P3 open counts don't match"),
                () -> assertEquals(1, incidentDetailsRepository
                                .countBySeverityAndStatusAndServiceDetails(Severity.P1.toString(),IncidentStatus.OPEN.toString(), serviceList.get(2)),
                        "IncidentDetailsRepositoryTest: FIX_ON_FAIL P1 open counts don't match")
        ));
    }

    @Test
    @DisplayName("testFindAllByServiceDetails()")
    void testFindAllByServiceDetails() {
        Optional<ServiceDetails> serviceDetailsFromDB = serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString());
        ServiceDetails serviceDetails = serviceList.get(0);
        serviceDetailsFromDB.ifPresent(details -> assertAll("Check Incident Counts",
                () -> assertEquals(2, incidentDetailsRepository.findAllByServiceDetails(this.serviceList.get(0)).size(), "IncidentDetailsRepositoryTest: List<IncidentDetails> counts don't match"),
                () -> assertEquals(1, incidentDetailsRepository.findAllByServiceDetails(this.serviceList.get(1)).size(), "IncidentDetailsRepositoryTest: List<IncidentDetails> counts don't match"),
                () -> assertEquals(1, incidentDetailsRepository.findAllByServiceDetails(this.serviceList.get(2)).size(), "IncidentDetailsRepositoryTest: List<IncidentDetails> counts don't match")
        ));
    }
}
