package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.Client;
import marcomanfrin.softwareops.enums.ClientType;
import marcomanfrin.softwareops.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(String name, ClientType type) {
        String validatedName = validateName(name);

         if (clientRepository.existsByNameIgnoreCase(validatedName)) {
             throw new IllegalArgumentException("Client name already exists");
         }

        Client client = new Client();
        client.setName(validatedName);
        client.setType(type);
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> getClientById(UUID id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client updateClient(UUID id, String name, ClientType type) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        client.setName(name);
        client.setType(type);
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(UUID id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found: " + id);
        }
        clientRepository.deleteById(id);
    }

    public List<Client> searchClients(String query) {
        String q = (query == null) ? "" : query.trim();
        if (q.isBlank()) return getAllClients();
        return clientRepository.findByNameContainingIgnoreCase(q);
    }

    private String validateName(String name) {
        if (name == null || name.trim().isBlank()) {
            throw new IllegalArgumentException("Client name cannot be blank");
        }
        return name.trim();
    }
}
