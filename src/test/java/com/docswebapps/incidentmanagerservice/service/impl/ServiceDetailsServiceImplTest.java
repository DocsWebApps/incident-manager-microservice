package com.docswebapps.incidentmanagerservice.service.impl;

import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import com.docswebapps.incidentmanagerservice.service.BaseDataMockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class ServiceDetailsServiceImplTest extends BaseDataMockTest {

    @Test
    @DisplayName("getAllServiceDetails()")
    void getAllServiceDetails() {
        when(this.serviceDetailsRepository.findAll()).thenReturn(serviceList);
        when(this.serviceMessageDetails.getMessageDetails(any(ServiceDetails.class))).thenReturn(Arrays.asList("1 incidents(s) in the last 24 hours","system-green"));
        assertEquals(3, this.serviceDetailsService.getAllServiceDetails().size());
    }

}
