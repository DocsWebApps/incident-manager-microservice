package com.docswebapps.incidentmanagerservice.web.controller.v1;

import com.docswebapps.incidentmanagerservice.domain.enumeration.IncidentStatus;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IncidentDetailsController.class)
class IncidentDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    void createIncident() throws Exception {
        String makeToJson = objectMapper.writeValueAsString(incidentDetailsDto);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeToJson))
                .andExpect(status().isCreated());
    }

}
