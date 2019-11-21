package com.docswebapps.incidentmanagerservice.service.impl;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.domain.enumeration.ServiceName;
import com.docswebapps.incidentmanagerservice.service.BaseDataMockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

;

@ExtendWith({MockitoExtension.class})
class IncidentDetailsServiceImplTest extends BaseDataMockTest {

    @Test
    @DisplayName("saveIncident()")
    void saveIncident() {
        // Happy Path
        when(this.serviceDetailsRepository.findByServiceName(ServiceName.PRE_PRODUCTION.toString())).thenReturn(Optional.of(serviceList.get(1)));
        when(incidentDetailsRepository.save(any(IncidentDetails.class))).thenReturn(incidentList.get(2));
        assertEquals(3L, incidentDetailsService.saveIncident(incidentDetailsDto), "saveIncidentHappyPath(): return values don't match");

        // Sad Path
        when(serviceDetailsRepository.findByServiceName(ServiceName.PRE_PRODUCTION.toString())).thenReturn(Optional.empty());
        assertEquals(-1L, incidentDetailsService.saveIncident(incidentDetailsDto), "saveIncidentSadPath(): return values don't match");
    }

    @Test
    @DisplayName("getAllIncidentsForService()")
    void getAllIncidentsForService() {
        // Happy Path
        lenient().when(this.serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString())).thenReturn(Optional.of(serviceList.get(0)));
        when(this.incidentDetailsRepository.findAllByServiceDetails(serviceList.get(0))).thenReturn(Arrays.asList(incidentList.get(0), incidentList.get(1)));
        assertEquals(2, this.incidentDetailsService.getAllIncidentsForService(ServiceName.PRODUCTION.toString()).size());

        // Sad Path
        lenient().when(this.serviceDetailsRepository.findByServiceName(ServiceName.PRODUCTION.toString())).thenReturn(Optional.empty());
        assertEquals(0, this.incidentDetailsService.getAllIncidentsForService(ServiceName.PRODUCTION.toString()).size());
    }

    @Test
    @DisplayName("deleteIncident()")
    void deleteIncident() {
        // Happy Path
        lenient().when(this.incidentDetailsRepository.findById(anyLong())).thenReturn(Optional.of(incidentList.get(0)));
        assertTrue(this.incidentDetailsService.deleteIncident(incidentList.get(0).getId()));

        // Sad Path
        lenient().when(this.incidentDetailsRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertFalse(this.incidentDetailsService.deleteIncident(incidentList.get(0).getId()));
    }

    @Test
    @DisplayName("closeIncident()")
    void closeIncident() {
        // Happy Path
        lenient().when(this.incidentDetailsRepository.findById(anyLong())).thenReturn(Optional.of(incidentList.get(0)));
        assertTrue(this.incidentDetailsService.closeIncident(incidentList.get(0).getId()));

        // Sad Path
        lenient().when(this.incidentDetailsRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertFalse(this.incidentDetailsService.closeIncident(incidentList.get(0).getId()));
    }

    @Test
    void updateIncident() {
        // Happy Path
        lenient().when(this.incidentDetailsRepository.findById(anyLong())).thenReturn(Optional.of(incidentList.get(0)));
        assertTrue(this.incidentDetailsService.updateIncident(incidentList.get(0).getId(), incidentDetailsDto));

        // Sad Path
        lenient().when(this.incidentDetailsRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertFalse(this.incidentDetailsService.updateIncident(incidentList.get(0).getId(), incidentDetailsDto));
    }

    @Test
    void getIncidentById() {
        // Happy Path - will return null since mapper is mocked
        when(this.incidentDetailsRepository.findById(anyLong())).thenReturn(Optional.of(incidentList.get(0)));
        assertFalse(this.incidentDetailsService.getIncidentById(1L).isPresent());
    }

    @Test
    void getAllIncidents() {
        // Happy Path
        when(this.incidentDetailsRepository.findAll()).thenReturn(incidentList);
        assertEquals(4, this.incidentDetailsService.getAllIncidents().size());
    }
}
