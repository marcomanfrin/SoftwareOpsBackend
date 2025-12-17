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
import java.util.stream.Stream;

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
        if (name == null || name.trim().isBlank()) {
            throw new IllegalArgumentException("Plant name cannot be blank");
        }
        if (orderNumber == null || orderNumber.trim().isBlank()) {
            throw new IllegalArgumentException("Order number cannot be blank");
        }

        Client primaryClient = clientRepository.findById(primaryClientId)
                .orElseThrow(() -> new RuntimeException("Primary client not found"));
        Client finalClient = clientRepository.findById(finalClientId)
                .orElseThrow(() -> new RuntimeException("Final client not found"));

        Plant plant = new Plant();
        plant.setName(name.trim());
        plant.setNotes(notes);
        plant.setOrderNumber(orderNumber.trim());
        plant.setPrimaryClient(primaryClient);
        plant.setFinalClient(finalClient);
        plant.setInvoiced(false);
        plant.setInvoicedAt(null);

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

        if (name != null && !name.trim().isBlank()) {
            plant.setName(name.trim());
        }
        if (orderNumber != null && !orderNumber.trim().isBlank()) {
            plant.setOrderNumber(orderNumber.trim());
        }
        plant.setNotes(notes);

        return plantRepository.save(plant);
    }

    @Override
    public void deletePlant(UUID id) {
        if (!plantRepository.existsById(id)) {
            throw new RuntimeException("Plant not found: " + id);
        }
        plantRepository.deleteById(id);
    }

    @Override
    public Plant invoicePlant(UUID id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plant not found: " + id));

        // idempotenza: se già fatturato, non cambiare nulla
        if (plant.isInvoiced()) {
            return plant;
        }

        plant.setInvoiced(true);
        plant.setInvoicedAt(LocalDateTime.now());
        return plantRepository.save(plant);
    }

    @Override
    public List<Plant> getPlantsByClient(UUID clientId) {
        List<Plant> primary = plantRepository.findByPrimaryClient_Id(clientId);
        List<Plant> fin = plantRepository.findByFinalClient_Id(clientId);

        return Stream.concat(primary.stream(), fin.stream())
                .distinct()
                .toList();
    }

    @Override
    public List<Plant> searchPlants(String query) {
        String q = (query == null) ? "" : query.trim();
        if (q.isBlank()) return getAllPlants();
        return plantRepository.search(q);
    }
}
