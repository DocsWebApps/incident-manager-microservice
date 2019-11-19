package com.docswebapps.incidentmanagerservice.service.impl;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.repository.IncidentDetailsRepository;
import com.docswebapps.incidentmanagerservice.repository.ServiceDetailsRepository;
import com.docswebapps.incidentmanagerservice.service.IncidentDetailsService;
import com.docswebapps.incidentmanagerservice.service.mappers.IncidentDetailsMapper;
import com.docswebapps.incidentmanagerservice.util.UpdateServiceDetailsStatus;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IncidentDetailsServiceImpl implements IncidentDetailsService {
    private final IncidentDetailsRepository incidentDetailsRepository;
    private final ServiceDetailsRepository serviceDetailsRepository;
    private final UpdateServiceDetailsStatus updateServiceDetailsStatus;
    private final IncidentDetailsMapper incidentDetailsMapper;

    public IncidentDetailsServiceImpl(IncidentDetailsRepository incidentDetailsRepository,
                                      ServiceDetailsRepository serviceDetailsRepository,
                                      UpdateServiceDetailsStatus updateServiceDetailsStatus,
                                      IncidentDetailsMapper incidentDetailsMapper) {
        this.incidentDetailsRepository = incidentDetailsRepository;
        this.serviceDetailsRepository = serviceDetailsRepository;
        this.updateServiceDetailsStatus = updateServiceDetailsStatus;
        this.incidentDetailsMapper = incidentDetailsMapper;
    }

    @Override
    public List<IncidentDetailsDto> getAllIncidentsForService(String serviceName) {
        log.info("IncidentDetailsServiceImpl: getAllIncidentsForService() method");
        return this.serviceDetailsRepository.findByServiceName(serviceName).map(
          serviceDetails -> this.incidentDetailsRepository
                      .findAllByServiceDetails(serviceDetails)
                      .stream()
                      .map(incidentDetailsMapper::entityToDto)
                      .collect(Collectors.toList())
        ).orElse(new ArrayList<>());
    }

    @Override
    public boolean deleteIncident(Long id) {
        log.info("IncidentDetailsServiceImpl: deleteIncident() method");
        return this.incidentDetailsRepository.findById(id)
                .map(incidentDetails -> {
                    this.incidentDetailsRepository.delete(incidentDetails);
                    this.updateServiceDetailsStatus.setServiceDetailsStatus(incidentDetails.getServiceDetails());
                    return true;
                }).orElse(false);
    }

    @Override
    public boolean closeIncident(Long id) {
        log.info("IncidentDetailsServiceImpl: closeIncident() method");
        return this.incidentDetailsRepository.findById(id)
                .map(incidentDetails -> {
                    ServiceDetails serviceDetails = incidentDetails.getServiceDetails();
                    Timestamp timeNow = Timestamp.valueOf(LocalDateTime.now());
                    incidentDetails.setClosedDate(timeNow);
                    incidentDetails.setStatus(IncidentStatus.CLOSED.toString());
                    this.incidentDetailsRepository.save(incidentDetails);
                    serviceDetails.setLastIncidentDate(timeNow);
                    serviceDetails.incrementPreviousIncidentCount();
                    this.updateServiceDetailsStatus.setServiceDetailsStatus(serviceDetails);
                    return true;
                }).orElse(false);
    }

    @Override
    public boolean updateIncident(Long id, IncidentDetailsDto incidentDetailsDto) {
        log.info("IncidentDetailsServiceImpl: updateIncident() method invoked");
        Optional<IncidentDetails> incidentDetailsOpt = this.incidentDetailsRepository.findById(id);
        return incidentDetailsOpt.map(
              incidentDetails -> {
                    incidentDetails.setDescription(incidentDetailsDto.getDescription());
                    incidentDetails.setSeverity(incidentDetailsDto.getSeverity().toString());
                    this.incidentDetailsRepository.save(incidentDetails);
                    this.updateServiceDetailsStatus.setServiceDetailsStatus(incidentDetails.getServiceDetails());
                    return true;
              }).orElse(false);
    }

    @Override
    public Optional<IncidentDetailsDto> getIncidentById(Long id) {
        log.info("IncidentDetailsServiceImpl: getIncidentById() method invoked");
        return this.incidentDetailsRepository.findById(id)
                .map(incidentDetailsMapper::entityToDto);
    }

    @Override
    public List<IncidentDetailsDto> getAllIncidents() {
        log.info("IncidentDetailsServiceImpl: getAllIncidents() method invoked");
        return this.incidentDetailsRepository.findAll()
                .stream()
                .map(incidentDetailsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long saveIncident(IncidentDetailsDto incidentDetailsDto) {
        log.info("IncidentDetailsServiceImpl: saveIncident() method invoked");
        Optional<ServiceDetails> serviceDetailsOpt = this.serviceDetailsRepository.findByServiceName(incidentDetailsDto.getServiceName().toString());
        return serviceDetailsOpt.map(serviceDetails -> {
            IncidentDetails newIncident = IncidentDetails.builder()
                    .description(incidentDetailsDto.getDescription())
                    .status(IncidentStatus.OPEN)
                    .severity(incidentDetailsDto.getSeverity())
                    .serviceDetails(serviceDetails)
                    .build();
            Long incidentId = this.incidentDetailsRepository.save(newIncident).getId();
            this.updateServiceDetailsStatus.setServiceDetailsStatus(serviceDetails);
            return incidentId;
        }).orElse(-1L);
    }
}
