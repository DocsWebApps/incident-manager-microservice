package com.docswebapps.incidentmanagerservice.service.impl;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.repository.IncidentDetailsRepository;
import com.docswebapps.incidentmanagerservice.repository.ServiceDetailsRepository;
import com.docswebapps.incidentmanagerservice.service.IncidentDetailsService;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IncidentDetailsServiceImpl implements IncidentDetailsService {
    private final IncidentDetailsRepository incidentDetailsRepository;
    private final ServiceDetailsRepository serviceDetailsRepository;

    public IncidentDetailsServiceImpl(IncidentDetailsRepository incidentDetailsRepository,
                                      ServiceDetailsRepository serviceDetailsRepository) {
        this.incidentDetailsRepository = incidentDetailsRepository;
        this.serviceDetailsRepository = serviceDetailsRepository;
    }

    @Override
    public Long saveIncident(IncidentDetailsDto incidentDetailsDto) {
        log.info("IncidentDetailsImpl: saveIncident() method {}", incidentDetailsDto.getSeverity());
        Optional<ServiceDetails> serviceDetails = this.serviceDetailsRepository.findByServiceName(incidentDetailsDto.getServiceName());
        if (serviceDetails.isPresent()) {
            IncidentDetails newIncident = IncidentDetails.builder()
                    .description(incidentDetailsDto.getDescription())
                    .status(incidentDetailsDto.getStatus())
                    .severity(incidentDetailsDto.getSeverity())
                    .serviceDetails(serviceDetails.get())
                    .build();

            return this.incidentDetailsRepository.save(newIncident).getId();
        } else {
            return -1L;
        }
    }
}
