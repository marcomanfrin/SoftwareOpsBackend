package marcomanfrin.softwareops.controllers;

import jakarta.validation.Valid;
import marcomanfrin.softwareops.DTO.plants.CreatePlantRequest;
import marcomanfrin.softwareops.DTO.plants.PlantResponse;
import marcomanfrin.softwareops.DTO.plants.UpdatePlantRequest;
import marcomanfrin.softwareops.DTO.works.WorkResponse;
import marcomanfrin.softwareops.entities.Plant;
import marcomanfrin.softwareops.entities.Work;
import marcomanfrin.softwareops.services.IPlantService;
import marcomanfrin.softwareops.services.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/plants")
public class PlantsController {

    @Autowired
    private IPlantService plantService;

    @Autowired
    private IWorkService workService;

    @PostMapping
    public ResponseEntity<PlantResponse> createPlant(@Valid @RequestBody CreatePlantRequest request) {
        PlantResponse created = plantService.createPlant(request);
        return ResponseEntity
                .created(URI.create("/plants/" + created.id()))
                .body(created);
    }

    @GetMapping
    public ResponseEntity<List<PlantResponse>> getAllPlants() {
        return ResponseEntity.ok(plantService.getAllPlants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantResponse> getPlantById(@PathVariable UUID id) {
        return ResponseEntity.ok(plantService.getPlantById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlantResponse> updatePlant(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePlantRequest request
    ) {
        return ResponseEntity.ok(plantService.updatePlant(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deletePlant(@PathVariable UUID id) {
        plantService.deletePlant(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/invoice")
    @PreAuthorize("@securityService.isAdministrative(authentication)")
    public ResponseEntity<PlantResponse> invoicePlant(@PathVariable UUID id) {
        return ResponseEntity.ok(plantService.invoicePlant(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<PlantResponse>> getPlantsByClient(@PathVariable UUID clientId) {
        return ResponseEntity.ok(plantService.getPlantsByClient(clientId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlantResponse>> searchPlants(@RequestParam("query") String query) {
        return ResponseEntity.ok(plantService.searchPlants(query));
    }

    @PostMapping("/{id}/works")
    @PreAuthorize("@securityService.isTechnician(authentication)")
    public ResponseEntity<WorkResponse> createWorkFromPlant(@PathVariable UUID id) {
        WorkResponse created = workService.createWorkFromPlant(id);
        return ResponseEntity
                .created(URI.create("/works/" + created.id()))
                .body(created);
    }
}
