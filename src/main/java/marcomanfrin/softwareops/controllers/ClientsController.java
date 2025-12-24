package marcomanfrin.softwareops.controllers;

import jakarta.validation.Valid;
import marcomanfrin.softwareops.DTO.clients.CreateClientRequest;
import marcomanfrin.softwareops.DTO.clients.UpdateClientRequest;
import marcomanfrin.softwareops.entities.Client;
import marcomanfrin.softwareops.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@RestController
@RequestMapping("/clients")
public class ClientsController {

    @Autowired
    private IClientService clientService;

    @PostMapping
    public ResponseEntity<Client> createClient(
            @Valid @RequestBody CreateClientRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        Client created = clientService.createClient(request.name(), request.type());

        URI location = uriBuilder
                .path("/clients/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable UUID id) {
        return clientService.getClientByIdOrThrow(id);
    }

    @PatchMapping("/{id}")
    public Client updateClient(@PathVariable UUID id, @Valid @RequestBody UpdateClientRequest request) {
        return clientService.updateClient(id, request.name(), request.type());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Client> searchClients(@RequestParam String query) {
        return clientService.searchClients(query);
    }
}
