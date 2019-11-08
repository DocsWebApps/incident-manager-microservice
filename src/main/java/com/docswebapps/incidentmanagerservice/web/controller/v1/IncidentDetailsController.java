package com.docswebapps.incidentmanagerservice.web.controller.v1;

import com.docswebapps.incidentmanagerservice.service.IncidentDetailsService;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
@Slf4j
@RequestMapping("/api/v1/incident-details")
public class IncidentDetailsController {
    private final IncidentDetailsService incidentDetailsService;

    public IncidentDetailsController(IncidentDetailsService incidentDetailsService) {
        this.incidentDetailsService = incidentDetailsService;
    }

    @PostMapping
    public ResponseEntity createIncident(@Valid @RequestBody IncidentDetailsDto incidentDetailsDto) throws URISyntaxException {
        log.info("IncidentDetailsController: createIncident() method");
        Long returnId = this.incidentDetailsService.saveIncident(incidentDetailsDto);
        if (returnId.intValue() > 0) {
            URI uri = new URI("/api/v1/incident-details/" + returnId.toString());
            return ResponseEntity.created(uri).build();
        } else {
            return ResponseEntity.badRequest().body("Error occurred creating incident. Contact an administrator!");
        }
    }

}
