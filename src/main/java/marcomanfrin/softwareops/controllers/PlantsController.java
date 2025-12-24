package marcomanfrin.softwareops.controllers;

import marcomanfrin.softwareops.DTO.plants.CreatePlantRequest;
import marcomanfrin.softwareops.DTO.plants.UpdatePlantRequest;
import marcomanfrin.softwareops.entities.Plant;
import marcomanfrin.softwareops.entities.Work;
import marcomanfrin.softwareops.services.IPlantService;
import marcomanfrin.softwareops.services.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Plant createPlant(@RequestBody CreatePlantRequest request) {
        return plantService.createPlant(
                request.name(),
                request.notes(),
                request.orderNumber(),
                request.primaryClientId(),
                request.finalClientId()
        );
    }

    @GetMapping
    public List<Plant> getAllPlants() {
        // TODO: more info
        return plantService.getAllPlants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlantById(@PathVariable UUID id) {
        return plantService.getPlantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Plant> updatePlant(@PathVariable UUID id, @RequestBody UpdatePlantRequest request) {
        Plant updatedPlant = plantService.updatePlant(id, request.name(), request.notes(), request.orderNumber());
        if (updatedPlant != null) {
            return ResponseEntity.ok(updatedPlant);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable UUID id) {
        plantService.deletePlant(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/invoice")
    public ResponseEntity<Plant> invoicePlant(@PathVariable UUID id) {
        Plant invoicedPlant = plantService.invoicePlant(id);
        if (invoicedPlant != null) {
            return ResponseEntity.ok(invoicedPlant);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/client/{clientId}")
    public List<Plant> getPlantsByClient(@PathVariable UUID clientId) {
        return plantService.getPlantsByClient(clientId);
    }

    @GetMapping("/search")
    public List<Plant> searchPlants(@RequestParam String query) {
        return plantService.searchPlants(query);
    }

    @PostMapping("/{id}/works")
    public Work createWorkFromPlant(@PathVariable UUID id) {
        return workService.createWorkFromPlant(id);
    }
}
