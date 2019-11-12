package com.docswebapps.incidentmanagerservice.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @Value("${com.docswebapps.testvalue}")
    private String testValue;

    @GetMapping
    public ResponseEntity getTest() {
        return ResponseEntity.ok().body(testValue);
    }
}
