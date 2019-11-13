package com.docswebapps.incidentmanagerservice.util;

import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import com.docswebapps.incidentmanagerservice.repository.IncidentDetailsRepository;
import com.docswebapps.incidentmanagerservice.repository.ServiceDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdateServiceDetailsStatus {
    private ServiceDetailsRepository serviceDetailsRepository;
    private IncidentDetailsRepository incidentDetailsRepository;

    @Autowired
    public void setServiceDetailsRepository(ServiceDetailsRepository serviceDetailsRepository) {
        this.serviceDetailsRepository = serviceDetailsRepository;
    }

    @Autowired
    public void setIncidentDetailsRepository(IncidentDetailsRepository incidentDetailsRepository) {
        this.incidentDetailsRepository = incidentDetailsRepository;
    }

    public void setServiceDetailsStatus(ServiceDetails serviceDetails) {
        if (incidentDetailsRepository.countBySeverityAndStatusAndServiceDetails(Severity.P1.toString(), IncidentStatus.OPEN.toString(), serviceDetails) > 0) {
            serviceDetails.setStatus(ServiceStatus.RED.toString());
        } else if (incidentDetailsRepository.countBySeverityAndStatusAndServiceDetails(Severity.P2.toString(), IncidentStatus.OPEN.toString(), serviceDetails) > 0) {
            serviceDetails.setStatus(ServiceStatus.AMBER.toString());
        } else {
            serviceDetails.setStatus(ServiceStatus.GREEN.toString());
        }
        this.serviceDetailsRepository.save(serviceDetails);
    }
}
