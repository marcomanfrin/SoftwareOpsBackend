package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.Client;
import marcomanfrin.softwareops.entities.Plant;
import marcomanfrin.softwareops.repositories.ClientRepository;
import marcomanfrin.softwareops.repositories.PlantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlantService implements IPlantService {

    private final PlantRepository plantRepository;
    private final ClientRepository clientRepository;

    public PlantService(PlantRepository plantRepository, ClientRepository clientRepository) {
        this.plantRepository = plantRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Plant createPlant(String name, String notes, String orderNumber, UUID primaryClientId, UUID finalClientId) {
        Client primaryClient = clientRepository.findById(primaryClientId)
                .orElseThrow(() -> new RuntimeException("Primary client not found"));
        Client finalClient = clientRepository.findById(finalClientId)
                .orElseThrow(() -> new RuntimeException("Final client not found"));

        Plant plant = new Plant();
        plant.setName(name);
        plant.setNotes(notes);
        plant.setOrderNumber(orderNumber);
        plant.setPrimaryClient(primaryClient);
        plant.setFinalClient(finalClient);
        plant.setInvoiced(false);

        return plantRepository.save(plant);
    }

    @Override
    public Optional<Plant> getPlantById(UUID id) {
        return plantRepository.findById(id);
    }

    @Override
    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    @Override
    public Plant updatePlant(UUID id, String name, String notes, String orderNumber) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plant not found"));

        plant.setName(name);
        plant.setNotes(notes);
        plant.setOrderNumber(orderNumber);

        return plantRepository.save(plant);
    }

    @Override
    public void deletePlant(UUID id) {
        plantRepository.deleteById(id);
    }

    @Override
    public Plant invoicePlant(UUID id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plant not found"));
        plant.setInvoiced(true);
        plant.setInvoicedAt(LocalDateTime.now());
        return plantRepository.save(plant);
    }
}
