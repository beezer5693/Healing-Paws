package org.brandon.healingpaws.client;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client saveClient(Client client) {
        Optional<Client> savedClient = clientRepository.findByEmail(client.getEmail());

        if (savedClient.isPresent()) {
            throw new RuntimeException("Client already exists");
        }

        return clientRepository.save(client);
    }

    public Client findClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    public Client updateClient(Long id, Client client) {
        client.setId(id);
        return clientRepository.save(client);
    }
}
