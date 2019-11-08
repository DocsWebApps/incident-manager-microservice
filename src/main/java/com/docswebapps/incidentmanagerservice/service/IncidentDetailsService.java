package com.docswebapps.incidentmanagerservice.service;

import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;

public interface IncidentDetailsService {
    Long saveIncident(IncidentDetailsDto incidentDetailsDto);
}
