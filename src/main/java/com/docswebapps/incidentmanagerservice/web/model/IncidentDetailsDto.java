package com.docswebapps.incidentmanagerservice.web.model;

import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class IncidentDetailsDto extends BaseDto {
    @NotBlank
    private String description;

    @Null
    private IncidentStatus status;

    @NotNull
    private Severity severity;

    @Null
    private OffsetDateTime closedDate;

    @NotNull
    private ServiceName serviceName;

    @Builder
    public IncidentDetailsDto(Long id, String description, IncidentStatus status, Severity severity,
                              OffsetDateTime closedDate, ServiceName serviceName, OffsetDateTime createdDate,
                              OffsetDateTime lastModifiedDate) {
        super(id, createdDate, lastModifiedDate);
        this.description = description;
        this.status = status;
        this.severity = severity;
        this.closedDate = closedDate;
        this.serviceName = serviceName;
    }
}
