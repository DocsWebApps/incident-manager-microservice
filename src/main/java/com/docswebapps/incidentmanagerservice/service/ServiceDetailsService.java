package com.docswebapps.incidentmanagerservice.service;
import com.docswebapps.incidentmanagerservice.web.model.ServiceDetailsDto;

import java.util.List;

public interface ServiceDetailsService {
    List<ServiceDetailsDto> getAllServiceDetails();
}
