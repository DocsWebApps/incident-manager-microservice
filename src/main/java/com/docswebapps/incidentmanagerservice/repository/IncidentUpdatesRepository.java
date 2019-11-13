package com.docswebapps.incidentmanagerservice.repository;

import com.docswebapps.incidentmanagerservice.domain.IncidentUpdates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentUpdatesRepository extends JpaRepository<IncidentUpdates, Long> {
}
