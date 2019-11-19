package com.docswebapps.incidentmanagerservice.service;

import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;

import java.util.List;
import java.util.Optional;

public interface IncidentDetailsService {
    Long saveIncident(IncidentDetailsDto incidentDetailsDto);
    List<IncidentDetailsDto> getAllIncidents();
    Optional<IncidentDetailsDto> getIncidentById(Long id);
    List<IncidentDetailsDto>  getAllIncidentsForService(String serviceName);
    boolean deleteIncident(Long id);
    boolean closeIncident(Long id);
    boolean updateIncident(Long id, IncidentDetailsDto incidentDetailsDto);
}
