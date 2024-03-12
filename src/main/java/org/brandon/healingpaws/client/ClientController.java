package org.brandon.healingpaws.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.saveClient(client));
    }

    @GetMapping("/{client-id}")
    public ResponseEntity<Client> getClientById(@PathVariable("client-id") Long id) {
        return ResponseEntity.ok(clientService.findClientById(id));
    }

    @PutMapping("/{client-id}")
    public ResponseEntity<Client> updateClient(@PathVariable("client-id") Long id, @RequestBody Client client) {
        return ResponseEntity.ok(clientService.updateClient(id, client));
    }

    @DeleteMapping("/{client-id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("client-id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
