package com.docswebapps.incidentmanagerservice.service.impl;

import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
import com.docswebapps.incidentmanagerservice.repository.ServiceDetailsRepository;
import com.docswebapps.incidentmanagerservice.service.ServiceDetailsService;
import com.docswebapps.incidentmanagerservice.service.mappers.DateMapper;
import com.docswebapps.incidentmanagerservice.util.ServiceMessageDetails;
import com.docswebapps.incidentmanagerservice.web.model.ServiceDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ServiceDetailsServiceImpl implements ServiceDetailsService {
    private final ServiceDetailsRepository serviceDetailsRepository;
    private final ServiceMessageDetails serviceMessageDetails;
    private final DateMapper dateMapper;

    public ServiceDetailsServiceImpl(ServiceDetailsRepository serviceDetailsRepository,
                                     ServiceMessageDetails serviceMessageDetails,
                                     DateMapper dateMapper) {
        this.serviceDetailsRepository = serviceDetailsRepository;
        this.serviceMessageDetails = serviceMessageDetails;
        this.dateMapper = dateMapper;
    }

    @Override
    public List<ServiceDetailsDto> getAllServiceDetails() {
        List<ServiceDetails> serviceDetailsList = serviceDetailsRepository.findAll();
        List<ServiceDetailsDto> serviceDetailsDtoList = new ArrayList<>();
        serviceDetailsList.forEach(serviceDetail -> {
            List<String> messageDetails = serviceMessageDetails.getMessageDetails(serviceDetail);
            serviceDetailsDtoList.add(ServiceDetailsDto.builder()
                    .id(serviceDetail.getId())
                    .serviceName(ServiceName.valueOf(serviceDetail.getServiceName()))
                    .status(ServiceStatus.valueOf(serviceDetail.getStatus()))
                    .previousIncidentCount(serviceDetail.getPreviousIncidentCount())
                    .message(messageDetails.get(0))
                    .messageColour(messageDetails.get(1))
                    .lastIncidentDate(dateMapper.asOffsetDateTime(serviceDetail.getLastIncidentDate()))
                    .createdDate(dateMapper.asOffsetDateTime(serviceDetail.getCreatedDate()))
                    .lastModifiedDate(dateMapper.asOffsetDateTime(serviceDetail.getLastModifiedDate()))
                    .build()
                    );
        });
        return serviceDetailsDtoList;
    }
}
