package com.docswebapps.incidentmanagerservice.repository;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentDetailsRepository extends JpaRepository<IncidentDetails, Long> {
    int countByStatusAndServiceDetails(String status, ServiceDetails serviceDetails);
}
