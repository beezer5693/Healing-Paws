package org.brandon.healingpaws.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> save(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.saveClient(client));
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAll() {
        return ResponseEntity.ok(clientService.findAllClients());
    }

    @GetMapping("/{client-id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable("client-id") Long id) {
        return ResponseEntity.ok(clientService.findClientById(id));
    }

    @PutMapping("/{client-id}")
    public ResponseEntity<ClientDTO> update(@PathVariable("client-id") Long id, @RequestBody ClientDTO dto) {
        return ResponseEntity.ok(clientService.updateClient(id, dto));
    }

    @DeleteMapping("/{client-id}")
    public ResponseEntity<Void> delete(@PathVariable("client-id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
