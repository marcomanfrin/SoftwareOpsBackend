package marcomanfrin.softwareops.controllers;

import marcomanfrin.softwareops.DTO.clients.CreateClientRequest;
import marcomanfrin.softwareops.DTO.clients.UpdateClientRequest;
import marcomanfrin.softwareops.entities.Client;
import marcomanfrin.softwareops.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientsController {

    @Autowired
    private IClientService clientService;

    @PostMapping
    public Client createClient(@RequestBody CreateClientRequest request) {
        return clientService.createClient(request.name(), request.type());
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable UUID id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable UUID id, @RequestBody UpdateClientRequest request) {
        Client updatedClient = clientService.updateClient(id, request.name(), request.type());
        if (updatedClient != null) {
            return ResponseEntity.ok(updatedClient);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Client> searchClients(@RequestParam String query) {
        return clientService.searchClients(query);
    }
}
