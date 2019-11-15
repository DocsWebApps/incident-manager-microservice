package com.docswebapps.incidentmanagerservice.web.controller.v1;

import com.docswebapps.incidentmanagerservice.service.ServiceDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok().headers(headers).body(serviceDetailsService.getAllServiceDetails());
    }

    // REMOVE IN PRODUCTION
    @Value("${com.docswebapps.testvalue}")
    private String testValue;

    @GetMapping("/test")
    public ResponseEntity getTest() {
        return ResponseEntity.ok().body(testValue);
    }

}
