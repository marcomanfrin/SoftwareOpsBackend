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
    public Client createClient(String name, String type) {
        Client client = new Client();
        client.setName(name);
        client.setType(ClientType.valueOf(type.toUpperCase()));
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
    public Client updateClient(UUID id, String name, String type) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        client.setName(name);
        client.setType(ClientType.valueOf(type.toUpperCase()));
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(UUID id) {
        clientRepository.deleteById(id);
    }
}
