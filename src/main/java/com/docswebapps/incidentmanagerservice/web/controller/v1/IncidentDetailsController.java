package com.docswebapps.incidentmanagerservice.web.controller.v1;

import com.docswebapps.incidentmanagerservice.service.IncidentDetailsService;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/v1/incident-details")
public class IncidentDetailsController {
    private final IncidentDetailsService incidentDetailsService;

    public IncidentDetailsController(IncidentDetailsService incidentDetailsService) {
        this.incidentDetailsService = incidentDetailsService;
    }

    @GetMapping
    public ResponseEntity getAllIncidents() {
        List<IncidentDetailsDto> allIncidents =  this.incidentDetailsService.getAllIncidents();
        return ResponseEntity.ok().body(allIncidents);
    }

    // Get One incident
    @GetMapping("/{id}")
    public ResponseEntity getIncidentById(@PathVariable("id") Long id) {
        Optional<IncidentDetailsDto> incidentOpt = this.incidentDetailsService.getIncidentById(id);
        return incidentOpt.isPresent()
                ? ResponseEntity.ok().body(incidentOpt.get())
                : ResponseEntity.notFound().build();
    }

    // Get all incidents for a service

    // Update an incident

    // Close and incident

    // Delete an incident

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
