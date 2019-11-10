package com.docswebapps.incidentmanagerservice.web.controller.v1;

import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import com.docswebapps.incidentmanagerservice.service.IncidentDetailsService;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Validation Tests with the IncidentDetailsController
@RunWith(SpringRunner.class)
@WebMvcTest(IncidentDetailsController.class)
@ExtendWith(MockitoExtension.class)
class IncidentDetailsControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IncidentDetailsService incidentDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String URL="/api/v1/incident-details";

    @Test
    public void createIncident() throws Exception {
        when(incidentDetailsService.saveIncident(any(IncidentDetailsDto.class))).thenReturn(1L);

        // Happy path : All service names
        Arrays.asList(ServiceName.values())
                .forEach(serviceName -> {
                    try {
                        this.createIncidentPost("{\"description\":\"Test Incident\",\"severity\":\"P4\",\"serviceName\":\"" + serviceName +"\"}"
                                , status().isCreated());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        // Happy path : All severities
        Arrays.asList(Severity.values())
                .forEach(severity -> {
                    try {
                        this.createIncidentPost(
                                "{\"description\":\"Test Incident\",\"severity\":\"" + severity + "\",\"serviceName\":\"PRODUCTION\"}"
                                , status().isCreated());
                    } catch(Exception ex) {
                        ex.printStackTrace();;
                    }
                });

        // Sad Path: NotNull id
        this.createIncidentPost(
                "{\"id\":\"1\",\"description\":\"Test Incident\",\"severity\":\"P4\",\"serviceName\":\"PRODUCTION\"}"
                , status().isBadRequest());

        // Sad Path: Severity outside Enum range
        this.createIncidentPost(
                "{\"description\":\"Test Incident\",\"severity\":\"P5\",\"serviceName\":\"PRE_PRODUCTION\"}"
                , status().isBadRequest());


        // Sad Path: Status outside Enum range
        this.createIncidentPost(
                "{\"description\":\"Test Incident\",\"status\":\"AJAR\",\"severity\":\"P4\",\"serviceName\":\"FIX_ON_FAIL\"}"
                , status().isBadRequest());

        // Sad Path: ServiceName outside Enum range
        this.createIncidentPost(
                "{\"description\":\"Test Incident\",\"severity\":\"P4\",\"serviceName\":\"BASILBRUSH\"}"
                , status().isBadRequest());

        // Sad Path: closedDate not null
        this.createIncidentPost(
                "{\"closedDate\":\"01-01-2019\",\"description\":\"Test Incident\",\"severity\":\"P4\",\"serviceName\":\"PRODUCTION\"}"
                , status().isBadRequest());

        // Sad Path: createdDate not null
        this.createIncidentPost(
                "{\"createdDate\":\"01-01-2019\",\"description\":\"Test Incident\",\"severity\":\"P4\",\"serviceName\":\"PRODUCTION\"}"
                , status().isBadRequest());

        // Sad Path: lastModifiedDate not null
        this.createIncidentPost(
                "{\"lastModifiedDate\":\"01-01-2019\",\"description\":\"Test Incident\",\"severity\":\"P4\",\"serviceName\":\"PRODUCTION\"}"
                , status().isBadRequest());

        // Sad Path: blank serviceName
        this.createIncidentPost(
                "{\"description\":\"Test Incident\",\"severity\":\"P4\",\"serviceName\":\"\"}"
                , status().isBadRequest());

        // Sad Path: status not null
        this.createIncidentPost(
                "{\"description\":\"Test Incident\",\"status\":\"OPEN\",\"severity\":\"P4\",\"serviceName\":\"PRODUCTION\"}"
                , status().isBadRequest());

        // Sad Path: Simulate DB issue
        when(incidentDetailsService.saveIncident(any(IncidentDetailsDto.class))).thenReturn(-1L);
        this.createIncidentPost(
                "{\"description\":\"Test Incident\",\"severity\":\"P4\",\"serviceName\":\"PRODUCTION\"}"
                , status().isBadRequest());

        // Check the number of service method invocations is 8
        verify(incidentDetailsService,times(8)).saveIncident(any(IncidentDetailsDto.class));
    }

    private void createIncidentPost(String jsonRequest, ResultMatcher result) throws Exception {
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(result);
    }

}
