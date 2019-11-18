package com.docswebapps.incidentmanagerservice.web.controller.v1;

import com.docswebapps.incidentmanagerservice.service.ServiceDetailsService;
import com.docswebapps.incidentmanagerservice.web.model.ServiceDetailsDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service-details")
public class ServiceDetailsController {
    private final ServiceDetailsService serviceDetailsService;

    public ServiceDetailsController(ServiceDetailsService serviceDetailsService) {
        this.serviceDetailsService = serviceDetailsService;
    }

    @GetMapping
    public ResponseEntity getAllServiceDetails() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        List<ServiceDetailsDto> allServices = serviceDetailsService.getAllServiceDetails();
        return allServices.size() > 0
                ? ResponseEntity.ok().headers(headers).body(allServices)
                : ResponseEntity.notFound().build();
    }

}
