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
    public ResponseEntity<List<IncidentDetailsDto>> getAllIncidents() {
        log.info("IncidentDetailsController: getAllIncidents() method");
        List<IncidentDetailsDto> allIncidents =  this.incidentDetailsService.getAllIncidents();
        return allIncidents.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(allIncidents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentDetailsDto> getIncidentById(@PathVariable("id") Long id) {
        log.info("IncidentDetailsController: getIncidentById() method");
        Optional<IncidentDetailsDto> incidentOpt = this.incidentDetailsService.getIncidentById(id);
        return incidentOpt
                .map(incidentDetailsDto -> ResponseEntity.ok().body(incidentDetailsDto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/service/{name}")
    public ResponseEntity<List<IncidentDetailsDto>> getAllIncidentsForService(@PathVariable("name") String name) {
        log.info("IncidentDetailsController: getAllIncidentsForService() method");
        List<IncidentDetailsDto> allIncidents = this.incidentDetailsService.getAllIncidentsForService(name);
        return allIncidents.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(allIncidents);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateIncident(@PathVariable("id") Long id, @Valid @RequestBody IncidentDetailsDto incidentDetailsDto) {
        log.info("IncidentDetailsController: updateIncident() method");
        return this.incidentDetailsService.updateIncident(id, incidentDetailsDto)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().body("Error updating incident. Contact an administrator!");
    }

    @DeleteMapping("/{id}/close")
    public ResponseEntity<String> closeIncident(@PathVariable("id") Long id) {
        log.info("IncidentDetailsController: closeIncident() method");
        return this.incidentDetailsService.closeIncident(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().body("Error closing incident. Contact an administrator!");

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteIncident(@PathVariable("id") Long id) {
        log.info("IncidentDetailsController: deleteIncident() method");
        return this.incidentDetailsService.deleteIncident(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().body("Error deleting incident. Contact an administrator!");
    }

    @PostMapping
    public ResponseEntity<String> createIncident(@Valid @RequestBody IncidentDetailsDto incidentDetailsDto) throws URISyntaxException {
        log.info("IncidentDetailsController: createIncident() method");
        Long returnId = this.incidentDetailsService.saveIncident(incidentDetailsDto);
        return returnId.intValue() > 0
                ? ResponseEntity.created(new URI("/api/v1/incident-details/" + returnId.toString())).build()
                : ResponseEntity.badRequest().body("Error creating incident. Contact an administrator!");
    }

}
