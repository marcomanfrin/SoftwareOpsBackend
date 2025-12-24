package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.DTO.plants.CreatePlantRequest;
import marcomanfrin.softwareops.DTO.plants.PlantResponse;
import marcomanfrin.softwareops.DTO.plants.UpdatePlantRequest;
import marcomanfrin.softwareops.entities.Plant;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface IPlantService {
    PlantResponse createPlant(CreatePlantRequest request);
    List<PlantResponse> getAllPlants();
    PlantResponse getPlantById(UUID id);
    PlantResponse updatePlant(UUID id, UpdatePlantRequest request);
    void deletePlant(UUID id);
    PlantResponse invoicePlant(UUID id);
    List<PlantResponse> getPlantsByClient(UUID clientId);
    List<PlantResponse> searchPlants(String query);
}
