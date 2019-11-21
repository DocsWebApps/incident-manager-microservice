package com.docswebapps.incidentmanagerservice.service;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import com.docswebapps.incidentmanagerservice.repository.IncidentDetailsRepository;
import com.docswebapps.incidentmanagerservice.repository.ServiceDetailsRepository;
import com.docswebapps.incidentmanagerservice.service.impl.IncidentDetailsServiceImpl;
import com.docswebapps.incidentmanagerservice.service.impl.ServiceDetailsServiceImpl;
import com.docswebapps.incidentmanagerservice.service.mappers.DateMapper;
import com.docswebapps.incidentmanagerservice.service.mappers.IncidentDetailsMapper;
import com.docswebapps.incidentmanagerservice.util.ServiceMessageDetails;
import com.docswebapps.incidentmanagerservice.util.UpdateServiceDetailsStatus;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BaseDataMockTest {
    @Mock
    public DateMapper dateMapper;

    @Mock
    public ServiceMessageDetails serviceMessageDetails;

    @Mock
    public IncidentDetailsMapper incidentDetailsMapper;

    @Mock
    public ServiceDetailsRepository serviceDetailsRepository;

    @Mock
    public IncidentDetailsRepository incidentDetailsRepository;

    @Mock
    public UpdateServiceDetailsStatus updateServiceDetailsStatus;

    @InjectMocks
    public ServiceDetailsServiceImpl serviceDetailsService;

    @InjectMocks
    public IncidentDetailsServiceImpl incidentDetailsService;

    public List<ServiceDetails> serviceList = Arrays.asList(
            ServiceDetails.builder()
                    .id(1L)
                    .serviceName(ServiceName.PRODUCTION)
                    .status(ServiceStatus.GREEN)
                    .previousIncidentCount(10L)
                    .lastIncidentDate(Timestamp.valueOf(LocalDateTime.now()))
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                    .version(0L)
                    .build(),
            ServiceDetails.builder()
                    .id(2L)
                    .serviceName(ServiceName.PRE_PRODUCTION)
                    .status(ServiceStatus.RED)
                    .previousIncidentCount(5L)
                    .lastIncidentDate(Timestamp.valueOf(LocalDateTime.now()))
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                    .version(0L)
                    .build(),
            ServiceDetails.builder()
                    .id(3L)
                    .serviceName(ServiceName.FIX_ON_FAIL)
                    .status(ServiceStatus.AMBER)
                    .previousIncidentCount(50L)
                    .lastIncidentDate(Timestamp.valueOf(LocalDateTime.now()))
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                    .version(0L)
                    .build()
    );

    public List<IncidentDetails> incidentList = Arrays.asList(
            IncidentDetails.builder()
                    .id(1L)
                    .serviceDetails(serviceList.get(0))
                    .status(IncidentStatus.OPEN)
                    .description("Production Incident")
                    .severity(Severity.P2)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                    .version(0L)
                    .build(),
            IncidentDetails.builder()
                    .id(2L)
                    .serviceDetails(serviceList.get(0))
                    .status(IncidentStatus.CLOSED)
                    .description("Production Incident")
                    .severity(Severity.P3)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                    .version(0L)
                    .build(),
            IncidentDetails.builder()
                    .id(3L)
                    .serviceDetails(serviceList.get(1))
                    .status(IncidentStatus.CLOSED)
                    .closedDate(Timestamp.valueOf(LocalDateTime.now()))
                    .description("Pre-Production Incident")
                    .severity(Severity.P2)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                    .version(0L)
                    .build(),
            IncidentDetails.builder()
                    .id(4L)
                    .serviceDetails(serviceList.get(2))
                    .status(IncidentStatus.OPEN)
                    .description("Fix On Fail Incident")
                    .severity(Severity.P1)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                    .version(0L)
                    .build()
    );

    public IncidentDetailsDto incidentDetailsDto = IncidentDetailsDto.builder()
            .description("Test Incident")
            .severity(Severity.P2)
            .serviceName(ServiceName.valueOf(serviceList.get(1).getServiceName()))
            .build();
}
