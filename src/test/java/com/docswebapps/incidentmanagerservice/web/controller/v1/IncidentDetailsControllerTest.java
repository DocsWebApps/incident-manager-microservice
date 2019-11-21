package com.docswebapps.incidentmanagerservice.web.controller.v1;

import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;
import com.docswebapps.incidentmanagerservice.service.IncidentDetailsService;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IncidentDetailsController.class)
class IncidentDetailsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    IncidentDetailsService incidentDetailsService;

    private final static String URL="/api/v1/incident-details";

    @Test
    @DisplayName("getAllIncidentsForService()")
    void getAllIncidentsForService() throws Exception {
        // Happy Path
        when(this.incidentDetailsService.getAllIncidentsForService(anyString()))
            .thenReturn(Collections.singletonList(IncidentDetailsDto.builder()
            .id(100L)
            .build()));

        mockMvc.perform(get(URL+"/service/production")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assertEquals("[{\"id\":100,\"createdDate\":null,\"lastModifiedDate\":null,\"description\":null,\"status\":null,\"severity\":null,\"closedDate\":null,\"serviceName\":null}]",
                            result.getResponse().getContentAsString()
                            );
                });

        when(this.incidentDetailsService.getAllIncidentsForService(anyString()))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(URL+"/service/production")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getIncidentById()")
    void getIncidentById() throws Exception {
        // Happy Path
        when(this.incidentDetailsService.getIncidentById(anyLong()))
                .thenReturn(Optional.of(IncidentDetailsDto.builder()
                        .id(100L).build()));

        mockMvc.perform(get(URL+"/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assertEquals("{\"id\":100,\"createdDate\":null,\"lastModifiedDate\":null,\"description\":null,\"status\":null,\"severity\":null,\"closedDate\":null,\"serviceName\":null}",
                            result.getResponse().getContentAsString());
                });
        // Sad Path
        when(this.incidentDetailsService.getIncidentById(anyLong()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get(URL+"/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getAllIncidents()")
    void getAllIncidents() throws Exception {
        // Happy Path
        when(this.incidentDetailsService.getAllIncidents())
                .thenReturn(Collections.singletonList(IncidentDetailsDto.builder()
                    .id(100L)
                    .build()));

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assertEquals("[{\"id\":100,\"createdDate\":null,\"lastModifiedDate\":null,\"description\":null,\"status\":null,\"severity\":null,\"closedDate\":null,\"serviceName\":null}]"
                            , result.getResponse().getContentAsString());
                });

        // Sad Path
        when(this.incidentDetailsService.getAllIncidents()).thenReturn(new ArrayList<>());
        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("updateIncident()")
    void updateIncident() throws Exception {
        // Happy Path
        when(incidentDetailsService.updateIncident(anyLong(), any(IncidentDetailsDto.class))).thenReturn(true);
        mockMvc.perform(put(URL+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Test Incident\",\"severity\":\"P4\",\"serviceName\":\"PRODUCTION\"}"))
                .andExpect(status().isNoContent());

        // Sad Path
        when(incidentDetailsService.updateIncident(anyLong(), any(IncidentDetailsDto.class))).thenReturn(false);
        mockMvc.perform(put(URL+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Test Incident\",\"severity\":\"P4\",\"serviceName\":\"PRODUCTION\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertEquals("Error updating incident. Contact an administrator!"
                            , result.getResponse().getContentAsString());
                });
    }

    @Test
    @DisplayName("closeIncident()")
    void closeIncident() throws Exception {
        // Happy Path
        when(incidentDetailsService.closeIncident(anyLong())).thenReturn(true);
        mockMvc.perform(delete(URL+"/1/close")).andExpect(status().isNoContent());

        // Sad Path
        when(incidentDetailsService.closeIncident(anyLong())).thenReturn(false);
        mockMvc.perform(delete(URL+"/1/close"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertEquals("Error closing incident. Contact an administrator!"
                            , result.getResponse().getContentAsString());
                });
    }

    @Test
    @DisplayName("deleteIncident()")
    void deleteIncident() throws Exception {
        // Happy Path
        when(incidentDetailsService.deleteIncident(anyLong())).thenReturn(true);
        mockMvc.perform(delete(URL+"/1/delete")).andExpect(status().isNoContent());

        // Sad Path
        when(incidentDetailsService.deleteIncident(anyLong())).thenReturn(false);
        mockMvc.perform(delete(URL+"/1/delete"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertEquals("Error deleting incident. Contact an administrator!"
                            , result.getResponse().getContentAsString());
        });
    }

    @Test
    @DisplayName("createIncident()")
    void createIncident() throws Exception {
        when(incidentDetailsService.saveIncident(any(IncidentDetailsDto.class))).thenReturn(1L);
        // Happy path : All valid service names
        Arrays.asList(ServiceName.values())
                .forEach(serviceName -> {
                    try {
                        this.createIncidentPost("{\"description\":\"Test Incident\",\"severity\":\"P4\",\"serviceName\":\"" + serviceName +"\"}"
                                , status().isCreated());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        // Happy path : All valid severities
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

         // Check the number of service method invocations is 7
        verify(incidentDetailsService,times(7)).saveIncident(any(IncidentDetailsDto.class));
    }

    @Test
    @DisplayName("dtoValidationTest()")
    void dtoValidationTest() throws Exception {
        when(incidentDetailsService.saveIncident(any(IncidentDetailsDto.class))).thenReturn(1L);
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

        // Check the number of service method invocations is 0, all should return isBadRequest()
        verify(incidentDetailsService,times(1)).saveIncident(any(IncidentDetailsDto.class));
    }

    void createIncidentPost(String jsonRequest, ResultMatcher result) throws Exception {
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(result);
    }

}
