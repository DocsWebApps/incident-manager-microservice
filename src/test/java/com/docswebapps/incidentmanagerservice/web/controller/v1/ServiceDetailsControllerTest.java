package com.docswebapps.incidentmanagerservice.web.controller.v1;

import com.docswebapps.incidentmanagerservice.service.ServiceDetailsService;
import com.docswebapps.incidentmanagerservice.web.model.ServiceDetailsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServiceDetailsController.class)
class ServiceDetailsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ServiceDetailsService serviceDetailsService;

    private final static String URL="/api/v1/service-details";

    @Test@DisplayName("getAllServiceDetails()")
    void getAllServiceDetails() throws Exception {
        // Happy Path
        when(serviceDetailsService.getAllServiceDetails())
                .thenReturn(Collections.singletonList(ServiceDetailsDto.builder()
                .id(1L)
                .build()));

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assertEquals("[{\"id\":1,\"createdDate\":null,\"lastModifiedDate\":null,\"serviceName\":null,\"status\":null,\"previousIncidentCount\":null,\"lastIncidentDate\":null,\"message\":null,\"messageColour\":null}]"
                            , result.getResponse().getContentAsString());
                });

        // Sad Path
        when(serviceDetailsService.getAllServiceDetails())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
