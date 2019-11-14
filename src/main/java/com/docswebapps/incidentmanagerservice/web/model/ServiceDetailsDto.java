package com.docswebapps.incidentmanagerservice.web.model;

import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ServiceDetailsDto extends BaseDto {
    @NotNull
    private ServiceName serviceName;

    @NotNull
    private ServiceStatus status;

    @NotNull
    private Long previousIncidentCount;

    private OffsetDateTime lastIncidentDate;

    @NotNull
    private String message;

    @NotNull
    private String messageColour;

    @Builder
    public ServiceDetailsDto(Long id, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                             ServiceName serviceName, ServiceStatus status, Long previousIncidentCount,
                             OffsetDateTime lastIncidentDate, String message, String messageColour) {
        super(id, createdDate, lastModifiedDate);
        this.serviceName = serviceName;
        this.status = status;
        this.previousIncidentCount = previousIncidentCount;
        this. lastIncidentDate = lastIncidentDate;
        this.message = message;
        this.messageColour = messageColour;
    }
}
