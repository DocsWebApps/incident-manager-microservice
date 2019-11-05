package com.docswebapps.incidentmanagerservice.domain;

import com.docswebapps.incidentmanagerservice.domain.enumeration.IssueStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

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

    @ManyToOne
    private ServiceDetails serviceDetails;

    @Builder
    public IncidentDetails(Long id,
                           Timestamp createdDate,
                           Timestamp lastModifiedDate,
                           Long version,
                           Timestamp closedDate,
                           IssueStatus status,
                           Severity severity,
                           ServiceDetails serviceDetails) {
        super(id, createdDate, lastModifiedDate,version);
        this.closedDate = closedDate;
        this.status = status;
        this.severity = severity;
        this.serviceDetails = serviceDetails;
    }

}
