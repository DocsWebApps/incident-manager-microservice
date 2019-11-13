package com.docswebapps.incidentmanagerservice.util;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import com.docswebapps.incidentmanagerservice.repository.IncidentDetailsRepository;
import com.docswebapps.incidentmanagerservice.repository.ServiceDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UpdateServiceDetailsStatusTest {

    @Autowired
    ServiceDetailsRepository serviceDetailsRepository;

    @Autowired
    IncidentDetailsRepository incidentDetailsRepository;

    private UpdateServiceDetailsStatus updateServiceDetailsStatus = new UpdateServiceDetailsStatus();

    private ServiceDetails serviceDetails = ServiceDetails.builder()
            .serviceName(ServiceName.PRODUCTION)
            .status(ServiceStatus.GREEN)
            .lastIncidentDate(null)
            .previousIncidentCount(5L)
            .build();

    private IncidentDetails incidentDetails = IncidentDetails.builder()
            .description("Test Incident")
            .severity(Severity.P1)
            .status(IncidentStatus.OPEN)
            .closedDate(null)
            .serviceDetails(serviceDetails)
            .build();

    @BeforeEach
    private void beforeEach() {
        updateServiceDetailsStatus.setIncidentDetailsRepository(incidentDetailsRepository);
        updateServiceDetailsStatus.setServiceDetailsRepository(serviceDetailsRepository);
        serviceDetailsRepository.save(serviceDetails);
        incidentDetailsRepository.save(incidentDetails);
    }

    @Test
    @DisplayName("Open P1 incident")
    public void openP1Incident() {
        updateServiceDetailsStatus.setServiceDetailsStatus(serviceDetails);
        assertEquals(ServiceStatus.RED.toString(), serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString()).get().getStatus(), "UpdateServiceDetailsStatusTest:openP1Incident() status mismatch");
    }

    @Test
    @DisplayName("Open P2 incident")
    public void openP2Incident() {
        incidentDetails.setSeverity(Severity.P2.toString());
        incidentDetailsRepository.save(incidentDetails);
        updateServiceDetailsStatus.setServiceDetailsStatus(serviceDetails);
        assertEquals(ServiceStatus.AMBER.toString(), serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString()).get().getStatus(), "UpdateServiceDetailsStatusTest:openP2Incident() status mismatch");

    }

    @Test
    @DisplayName("No open P1/P2 incidents")
    public void noOpenP1P2Incident() {
        incidentDetails.setSeverity(Severity.P3.toString());
        incidentDetailsRepository.save(incidentDetails);
        updateServiceDetailsStatus.setServiceDetailsStatus(serviceDetails);
        assertEquals(ServiceStatus.GREEN.toString(), serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString()).get().getStatus(), "UpdateServiceDetailsStatusTest:openP2Incident() status mismatch");
    }

}
