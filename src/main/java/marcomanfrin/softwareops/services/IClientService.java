package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.Client;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface IClientService {
    Client createClient(String name, String type);
    Optional<Client> getClientById(UUID id);
    List<Client> getAllClients();
    Client updateClient(UUID id, String name, String type);
    void deleteClient(UUID id);
}
