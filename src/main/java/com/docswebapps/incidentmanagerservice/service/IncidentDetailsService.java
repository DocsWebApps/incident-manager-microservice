package com.docswebapps.incidentmanagerservice.service;

import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;

import java.util.List;
import java.util.Optional;

public interface IncidentDetailsService {
    Long saveIncident(IncidentDetailsDto incidentDetailsDto);
    List<IncidentDetailsDto> getAllIncidents();
    Optional<IncidentDetailsDto> getIncidentById(Long id);
}
