package com.docswebapps.incidentmanagerservice.web.controller.v1;

import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import com.docswebapps.incidentmanagerservice.service.IncidentDetailsService;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(IncidentDetailsController.class)
@ExtendWith(MockitoExtension.class)
class IncidentDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IncidentDetailsService incidentDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private IncidentDetailsDto incidentDetailsDto = IncidentDetailsDto.builder()
            .description("Test Incident")
            .serviceName("Production")
            .severity(Severity.P2)
            .status(IncidentStatus.OPEN)
            .build();

    private final String URL="/api/v1/incident-details";

    @Test
    public void createIncident() throws Exception {
        String makeToJson = objectMapper.writeValueAsString(incidentDetailsDto);
        Mockito.when(incidentDetailsService.saveIncident(any(IncidentDetailsDto.class))).thenReturn(1L);

        // Happy Path
        this.createIncidentPost(makeToJson,status().isCreated());

        // Sad Path: Severity outside Enum range
        makeToJson = "{\"description\":\"TestValue111\",\"status\":\"OPEN\",\"severity\":\"P5\",\"serviceName\":\"Production\"}";
        this.createIncidentPost(makeToJson, status().isBadRequest());

        // Sad Path: NotNull id
        makeToJson = "{\"id\":\"1\",\"description\":\"TestValue111\",\"status\":\"OPEN\",\"severity\":\"P4\",\"serviceName\":\"Production\"}";
        this.createIncidentPost(makeToJson, status().isBadRequest());

        // Sad Path: Status outside Enum range
        makeToJson = "{\"description\":\"TestValue111\",\"status\":\"AJAR\",\"severity\":\"P4\",\"serviceName\":\"Production\"}";
        this.createIncidentPost(makeToJson, status().isBadRequest());

        // Sad Path: closedDate not null
        makeToJson = "{\"closedDate\":\"01-01-2019\",\"description\":\"TestValue111\",\"status\":\"OPEN\",\"severity\":\"P4\",\"serviceName\":\"Production\"}";
        this.createIncidentPost(makeToJson, status().isBadRequest());

        // Sad Path: createdDate not null
        makeToJson = "{\"createdDate\":\"01-01-2019\",\"description\":\"TestValue111\",\"status\":\"OPEN\",\"severity\":\"P4\",\"serviceName\":\"Production\"}";
        this.createIncidentPost(makeToJson, status().isBadRequest());

        // Sad Path: lastModifiedDate not null
        makeToJson = "{\"lastModifiedDate\":\"01-01-2019\",\"description\":\"TestValue111\",\"status\":\"OPEN\",\"severity\":\"P4\",\"serviceName\":\"Production\"}";
        this.createIncidentPost(makeToJson, status().isBadRequest());

        // Sad Path: blank serviceName
        makeToJson = "{\"description\":\"TestValue111\",\"status\":\"OPEN\",\"severity\":\"P4\",\"serviceName\":\"\"}";
        this.createIncidentPost(makeToJson, status().isBadRequest());

        // Sad Path: Simulate DB issue
        Mockito.when(incidentDetailsService.saveIncident(any(IncidentDetailsDto.class))).thenReturn(-1L);
        makeToJson = "{\"description\":\"TestValue111\",\"status\":\"OPEN\",\"severity\":\"P4\",\"serviceName\":\"Production\"}";
        this.createIncidentPost(makeToJson, status().isBadRequest());
    }

    private void createIncidentPost(String jsonRequest, ResultMatcher result) throws Exception {
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(result);
    }

}
