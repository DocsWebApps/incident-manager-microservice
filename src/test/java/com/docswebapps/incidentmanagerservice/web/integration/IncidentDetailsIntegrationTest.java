package com.docswebapps.incidentmanagerservice.web.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IncidentDetailsIntegrationTest {

    @Test
    void myTest() {
        assertAll("My Tests",
                () -> assertEquals("D","D","Expected and Actual Do not match"),
                () -> assertEquals("D","D","Expected and Actual Do not match"));
    }

}
