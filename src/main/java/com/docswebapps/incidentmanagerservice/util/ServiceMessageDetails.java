package com.docswebapps.incidentmanagerservice.util;

import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.repository.IncidentDetailsRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ServiceMessageDetails {
    private IncidentDetailsRepository incidentDetailsRepository;
    private List<String> messageDetails = new ArrayList<>();

    public void setIncidentDetailsRepository(IncidentDetailsRepository incidentDetailsRepository) {
        this.incidentDetailsRepository = incidentDetailsRepository;
    }

    public List<String> getMessageDetails(ServiceDetails serviceDetails) {
        int incidentCount = incidentDetailsRepository.countByStatusAndServiceDetails(IncidentStatus.CLOSED.toString(), serviceDetails);
        Timestamp lastIncidentDate = serviceDetails.getLastIncidentDate();

        AddToList messageList = (message, colour) -> { this.messageDetails.add(0, message); this.messageDetails.add(1, colour);};

        if (incidentCount > 0) {
            messageList.add(incidentCount + " incidents(s) in the last 24 hours", "system-red");
        } else if (lastIncidentDate == null) {
            messageList.add("No previous incidents recorded", "system-green");
        } else {
            Long daysSinceLastIncident = ChronoUnit.DAYS.between(lastIncidentDate.toLocalDateTime(), LocalDateTime.now());
            messageList.add(daysSinceLastIncident + " Day(s) since last incident", "system-green");
        }

        return this.messageDetails;
    }
}

interface AddToList { void add(String message, String colour);}
