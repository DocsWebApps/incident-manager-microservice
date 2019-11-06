package com.docswebapps.incidentmanagerservice.repository;

import com.docswebapps.incidentmanagerservice.domain.IncidentUpdates;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IncidentUpdatesRepository extends PagingAndSortingRepository<IncidentUpdates, Long> {
}
