package com.docswebapps.incidentmanagerservice.web.controller.v1;

import com.docswebapps.incidentmanagerservice.service.IncidentDetailsService;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
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
        log.info("IncidentDetailsController: getAllIncidents) method");
        List<IncidentDetailsDto> allIncidents =  this.incidentDetailsService.getAllIncidents();
        return allIncidents.size() > 0
                ? ResponseEntity.ok().body(allIncidents)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getIncidentById(@PathVariable("id") Long id) {
        log.info("IncidentDetailsController: getIncidentById() method");
        Optional<IncidentDetailsDto> incidentOpt = this.incidentDetailsService.getIncidentById(id);
        return incidentOpt.isPresent()
                ? ResponseEntity.ok().body(incidentOpt.get())
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/service/{name}")
    public ResponseEntity getAllIncidentsForService(@PathVariable("name") String name) {
        log.info("IncidentDetailsController: getAllIncidentsForService() method");
        List<IncidentDetailsDto> allIncidents = this.incidentDetailsService.getAllIncidentsForService(name);
        return allIncidents.size() > 0
                ? ResponseEntity.ok().body(allIncidents)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateIncident(@PathVariable("id") Long id, @Valid @RequestBody IncidentDetailsDto incidentDetailsDto) {
        log.info("IncidentDetailsController: updateIncident() method");
        return this.incidentDetailsService.updateIncident(id, incidentDetailsDto)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().body("Error updating incident. Contact an administrator!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrCloseIncident(@PathVariable("id") Long id, @Param("type") String type) {
        log.info("IncidentDetailsController: deleteOrCloseIncident() method");
        return this.incidentDetailsService.deleteOrCloseIncident(id, type)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().body("Error deleting incident. Contact an administrator!");
    }

    @PostMapping
    public ResponseEntity createIncident(@Valid @RequestBody IncidentDetailsDto incidentDetailsDto) throws URISyntaxException {
        log.info("IncidentDetailsController: createIncident() method");
        Long returnId = this.incidentDetailsService.saveIncident(incidentDetailsDto);
        if (returnId.intValue() > 0) {
            URI uri = new URI("/api/v1/incident-details/" + returnId.toString());
            return ResponseEntity.created(uri).build();
        } else {
            return ResponseEntity.badRequest().body("Error creating incident. Contact an administrator!");
        }
    }

}
