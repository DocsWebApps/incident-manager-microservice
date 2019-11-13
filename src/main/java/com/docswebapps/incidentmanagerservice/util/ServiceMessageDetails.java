package com.docswebapps.incidentmanagerservice.util;

import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.repository.IncidentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ServiceMessageDetails {
    private IncidentDetailsRepository incidentDetailsRepository;
    private List<String> messageDetails = new ArrayList<>();

    @Autowired
    public void setIncidentDetailsRepository(IncidentDetailsRepository incidentDetailsRepository) {
        this.incidentDetailsRepository = incidentDetailsRepository;
    }

    public List<String> getMessageDetails(ServiceDetails serviceDetails) {
        int incidentCount = incidentDetailsRepository.countByStatusAndServiceDetails(IncidentStatus.CLOSED.toString(), serviceDetails);
        Timestamp lastIncidentDate = serviceDetails.getLastIncidentDate();
        BiConsumer<String, String> messageList = (message, colour) -> { this.messageDetails.add(0, message); this.messageDetails.add(1, colour);};

        if (incidentCount > 0) {
            messageList.accept(incidentCount + " incidents(s) in the last 24 hours", "system-red");
        } else if (lastIncidentDate == null) {
            messageList.accept("No previous incidents recorded", "system-green");
        } else {
            long daysSinceLastIncident = ChronoUnit.DAYS.between(lastIncidentDate.toLocalDateTime(), LocalDateTime.now());
            messageList.accept(daysSinceLastIncident + " Day(s) since last incident", "system-green");
        }
        return this.messageDetails;
    }
}
