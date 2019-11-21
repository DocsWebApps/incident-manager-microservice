package com.docswebapps.incidentmanagerservice.service.mappers;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class IncidentDetailsMapperTest {
    @MockBean
    public DateMapper dateMapper;

    IncidentDetails incidentDetails = IncidentDetails.builder()
            .id(1L)
            .serviceDetails(ServiceDetails.builder()
                    .serviceName(ServiceName.PRODUCTION)
                    .status(ServiceStatus.GREEN)
                    .previousIncidentCount(0L)
                    .lastModifiedDate(null)
                    .build())
            .status(IncidentStatus.OPEN)
            .description("Production Incident")
            .severity(Severity.P2)
            .closedDate(null)
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
            .version(0L)
            .build();

    IncidentDetailsDto incidentDetailsDto = IncidentDetailsDto.builder()
            .description("Production Incident")
            .severity(Severity.P2)
            .serviceName(ServiceName.PRODUCTION)
            .build();

    IncidentDetailsMapper incidentDetailsMapper = new IncidentDetailsMapperImpl();

//    @Test
//    @DisplayName("entityToDto()")
//    void entityToDto() {
//        when(dateMapper.asOffsetDateTime(any(Timestamp.class))).thenReturn(null);
//        IncidentDetailsDto dto = this.incidentDetailsMapper.entityToDto(incidentDetails);
//        assertTrue(dto instanceof IncidentDetailsDto);
//        assertEquals(ServiceName.PRODUCTION, dto.getServiceName());
//    }

}
