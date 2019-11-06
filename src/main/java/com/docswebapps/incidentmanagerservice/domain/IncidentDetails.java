package com.docswebapps.incidentmanagerservice.domain;

import com.docswebapps.incidentmanagerservice.domain.enumeration.IssueStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
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
@Table(name = "incident_details")
public class IncidentDetails extends Base {
    @Column
    private Timestamp closedDate;

    @Column(nullable = false)
    private IssueStatus status;

    @Column(nullable = false)
    private Severity severity;

    @Column
    private String description;

    @ManyToOne
    private ServiceDetails serviceDetails;

    @OneToMany(mappedBy = "incidentDetails", fetch = FetchType.LAZY)
    private Collection<IncidentUpdates> incidentUpdates;

    @Builder
    public IncidentDetails(Long id,
                           Timestamp createdDate,
                           Timestamp lastModifiedDate,
                           Long version,
                           Timestamp closedDate,
                           IssueStatus status,
                           Severity severity,
                           String description,
                           ServiceDetails serviceDetails,
                           Collection<IncidentUpdates> incidentUpdates) {
        super(id, createdDate, lastModifiedDate,version);
        this.closedDate = closedDate;
        this.status = status;
        this.severity = severity;
        this.description = description;
        this.serviceDetails = serviceDetails;
        this.incidentUpdates = incidentUpdates;
    }

}
