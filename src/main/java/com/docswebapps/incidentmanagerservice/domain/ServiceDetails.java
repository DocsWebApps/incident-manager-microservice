package com.docswebapps.incidentmanagerservice.domain;

import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "service_details")
public class ServiceDetails extends Base {
    @Column(unique = true, nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private ServiceStatus status;

    @Column(nullable = false)
    private Long previousIncidentCount;

    @Column
    private Timestamp lastIncidentDate;

    @OneToMany(mappedBy = "serviceDetails", fetch = FetchType.LAZY)
    private Collection<IncidentDetails> incidents;

    @Builder
    public ServiceDetails(Long id,
                          Timestamp createdDate,
                          Timestamp lastModifiedDate,
                          Long version,
                          String serviceName,
                          ServiceStatus status,
                          Long previousIncidentCount,
                          Timestamp lastIncidentDate,
                          Collection<IncidentDetails> incidents) {
        super(id, createdDate, lastModifiedDate, version);
        this.serviceName = serviceName;
        this.status = status;
        this.previousIncidentCount = previousIncidentCount;
        this.lastIncidentDate = lastIncidentDate;
        this.incidents = incidents;
    }

}
