package com.docswebapps.incidentmanagerservice.domain;

import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
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
    private String status;

    @Column(nullable = false)
    private String severity;

    @Column
    private String description;

    @ManyToOne
    private ServiceDetails serviceDetails;

    @OneToMany(mappedBy = "incidentDetails", fetch = FetchType.LAZY)
    private Collection<IncidentUpdates> incidentUpdates;

    public IncidentDetails(Long id,
                           Timestamp createdDate,
                           Timestamp lastModifiedDate,
                           Long version,
                           Timestamp closedDate,
                           IncidentStatus status,
                           Severity severity,
                           String description,
                           ServiceDetails serviceDetails,
                           Collection<IncidentUpdates> incidentUpdates) {
        super(id, createdDate, lastModifiedDate,version);
        this.closedDate = closedDate;
        this.status = status.toString();
        this.severity = severity.toString();
        this.description = description;
        this.serviceDetails = serviceDetails;
        this.incidentUpdates = incidentUpdates;
    }

    public static IncidentDetailsBuilder builder() {
        return new IncidentDetailsBuilder();
    }

    public static class IncidentDetailsBuilder {
        private Long id;
        private Timestamp createdDate;
        private Timestamp lastModifiedDate;
        private Long version;
        private Timestamp closedDate;
        private IncidentStatus status;
        private Severity severity;
        private String description;
        private ServiceDetails serviceDetails;
        private Collection<IncidentUpdates> incidentUpdates;

        IncidentDetailsBuilder() {
        }

        public IncidentDetailsBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public IncidentDetailsBuilder createdDate(Timestamp createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public IncidentDetailsBuilder lastModifiedDate(Timestamp lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
            return this;
        }

        public IncidentDetailsBuilder version(Long version) {
            this.version = version;
            return this;
        }

        public IncidentDetailsBuilder closedDate(Timestamp closedDate) {
            this.closedDate = closedDate;
            return this;
        }

        public IncidentDetailsBuilder status(IncidentStatus status) {
            this.status = status;
            return this;
        }

        public IncidentDetailsBuilder severity(Severity severity) {
            this.severity = severity;
            return this;
        }

        public IncidentDetailsBuilder description(String description) {
            this.description = description;
            return this;
        }

        public IncidentDetailsBuilder serviceDetails(ServiceDetails serviceDetails) {
            this.serviceDetails = serviceDetails;
            return this;
        }

        public IncidentDetailsBuilder incidentUpdates(Collection<IncidentUpdates> incidentUpdates) {
            this.incidentUpdates = incidentUpdates;
            return this;
        }

        public IncidentDetails build() {
            return new IncidentDetails(id, createdDate, lastModifiedDate, version, closedDate, status, severity, description, serviceDetails, incidentUpdates);
        }

        public String toString() {
            return "IncidentDetails.IncidentDetailsBuilder(id=" + this.id + ", createdDate=" + this.createdDate + ", lastModifiedDate=" + this.lastModifiedDate + ", version=" + this.version + ", closedDate=" + this.closedDate + ", status=" + this.status + ", severity=" + this.severity + ", description=" + this.description + ", serviceDetails=" + this.serviceDetails + ", incidentUpdates=" + this.incidentUpdates + ")";
        }
    }
}
