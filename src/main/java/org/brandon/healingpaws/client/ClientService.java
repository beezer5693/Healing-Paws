package org.brandon.healingpaws.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;


    public ClientDTO saveClient(Client client) {
        if (clientAlreadyExists(client)) {
            throw new RuntimeException("Client already exists");
        }
        Client savedClient = clientRepository.save(client);
        return clientMapper.mapClientToDto(savedClient);
    }


    public List<ClientDTO> findAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::mapClientToDto)
                .toList();
    }


    public ClientDTO findClientById(Long id) {
        return clientRepository.findById(id)
                .stream()
                .map(clientMapper::mapClientToDto)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }


    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    public ClientDTO updateClient(Long id, ClientDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not update client. Client not found"));
        Client clientWithUpdatedInfo = updateClientInfo(id, dto, client);
        Client updatedClient = clientRepository.save(clientWithUpdatedInfo);
        return clientMapper.mapClientToDto(updatedClient);
    }

    private static Client updateClientInfo(Long id, ClientDTO dto, Client client) {
        return Client.builder()
                .id(id)
                .firstName(checkForNullValue(dto.firstName(), client.getFirstName()))
                .lastName(checkForNullValue(dto.lastName(), client.getLastName()))
                .email(checkForNullValue(dto.email(), client.getEmail()))
                .phone(checkForNullValue(dto.phone(), client.getPhone()))
                .password(client.getPassword())
                .build();
    }

    private static String checkForNullValue(String input, String existingValue) {
        return input != null ? input : existingValue;
    }

    private boolean clientAlreadyExists(Client client) {
        Optional<Client> savedClient = clientRepository.findByEmail(client.getEmail());
        return savedClient.isPresent();
    }
}
