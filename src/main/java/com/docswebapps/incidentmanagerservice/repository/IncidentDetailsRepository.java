package com.docswebapps.incidentmanagerservice.repository;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IncidentDetailsRepository extends PagingAndSortingRepository<IncidentDetails, Long> {
}
