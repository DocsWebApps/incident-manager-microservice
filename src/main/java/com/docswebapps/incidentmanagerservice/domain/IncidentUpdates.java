package com.docswebapps.incidentmanagerservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "incident_updates")
public class IncidentUpdates extends Base {
    @Column(nullable = false)
    private String updateText;

    @ManyToOne
    private IncidentDetails incidentDetails;

    @Builder
    public IncidentUpdates(Long id, String updateText,
                           Timestamp createdDate, Timestamp lastModifiedDate,
                           Long version, IncidentDetails incidentDetails) {
        super(id, createdDate, lastModifiedDate, version);
        this.updateText = updateText;
        this.incidentDetails = incidentDetails;
    }

}
