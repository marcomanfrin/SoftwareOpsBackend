package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.Plant;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface IPlantService {
    Plant createPlant(String name, String notes, String orderNumber, UUID primaryClientId, UUID finalClientId);
    Optional<Plant> getPlantById(UUID id);
    List<Plant> getAllPlants();
    Plant updatePlant(UUID id, String name, String notes, String orderNumber);
    void deletePlant(UUID id);
    Plant invoicePlant(UUID id);
    List<Plant> getPlantsByClient(UUID clientId);
    List<Plant> searchPlants(String query);
}
