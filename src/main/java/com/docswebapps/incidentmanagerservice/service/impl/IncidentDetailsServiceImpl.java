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
        log.info("IncidentDetailsServiceImpl: getAllIncidentsForService() method invoked");
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
        return false;
    }

    @Override
    public boolean closeIncident(Long id) {
        return false;
    }

//    @Override
//    public boolean deleteOrCloseIncident(Long id, String type) {
//        log.info("IncidentDetailsServiceImpl: deleteOrCloseIncident() method invoked");
//        IncidentDetails incidentDetails;
//        ServiceDetails serviceDetails;
//        LocalDateTime dateTimeNow = LocalDateTime.now();
//        Optional<IncidentDetails> incidentDetailsOpt = this.incidentDetailsRepository.findById(id);
//        if (type.equals("delete") && incidentDetailsOpt.isPresent()) {
//            incidentDetails = incidentDetailsOpt.get();
//            serviceDetails = this.serviceDetailsRepository.getOne(incidentDetails.getServiceDetails().getId());
//            this.incidentDetailsRepository.deleteById(id);
//            updateServiceDetailsStatus.setServiceDetailsStatus(serviceDetails);
//            return true;
//        } else if (type.equals("close") && incidentDetailsOpt.isPresent()) {
//            incidentDetails = incidentDetailsOpt.get();
//            serviceDetails = this.serviceDetailsRepository.getOne(incidentDetails.getServiceDetails().getId());
//            incidentDetails.setClosedDate(Timestamp.valueOf(dateTimeNow));
//            incidentDetails.setStatus(IncidentStatus.CLOSED.toString());
//            this.incidentDetailsRepository.save(incidentDetails);
//            serviceDetails.setLastIncidentDate(Timestamp.valueOf(dateTimeNow));
//            serviceDetails.incrementPreviousIncidentCount();
//            this.serviceDetailsRepository.save(serviceDetails);
//            updateServiceDetailsStatus.setServiceDetailsStatus(serviceDetails);
//            return true;
//        } else {
//            return false;
//        }
//    }

    @Override
    public boolean updateIncident(Long id, IncidentDetailsDto incidentDetailsDto) {
        log.info("IncidentDetailsServiceImpl: updateIncident() method invoked");
        Optional<IncidentDetails> incidentDetailsOpt = this.incidentDetailsRepository.findById(id);
        if (incidentDetailsOpt.isPresent()) {
            IncidentDetails incidentDetails = incidentDetailsOpt.get();
            incidentDetails.setDescription(incidentDetailsDto.getDescription());
            incidentDetails.setSeverity(incidentDetailsDto.getSeverity().toString());
            this.incidentDetailsRepository.save(incidentDetails);
            updateServiceDetailsStatus.setServiceDetailsStatus(incidentDetails.getServiceDetails());
            return true;
        } else {
            return false;
        }
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
        Long incidentId;
        Optional<ServiceDetails> serviceDetailsOpt = this.serviceDetailsRepository.findByServiceName(incidentDetailsDto.getServiceName().toString());
        if (serviceDetailsOpt.isPresent()) {
            ServiceDetails serviceDetails = serviceDetailsOpt.get();
            IncidentDetails newIncident = IncidentDetails.builder()
                    .description(incidentDetailsDto.getDescription())
                    .status(IncidentStatus.OPEN)
                    .severity(incidentDetailsDto.getSeverity())
                    .serviceDetails(serviceDetails)
                    .build();
            incidentId = this.incidentDetailsRepository.save(newIncident).getId();
            updateServiceDetailsStatus.setServiceDetailsStatus(serviceDetails);
            return incidentId;
        } else {
            return -1L;
        }
    }
}
