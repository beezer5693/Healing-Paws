package org.brandon.healingpaws.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brandon.healingpaws.exceptions.ClientAlreadyExistsException;
import org.brandon.healingpaws.exceptions.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClientDTO saveClient(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            log.error("[ClientServiceImpl::SaveClient] Client with email [{}] already exists", client.getEmail());
            throw new ClientAlreadyExistsException(client.getEmail());
        }

        Client savedClient = clientRepository.save(client);

        return modelMapper.map(savedClient, ClientDTO.class);
    }

    @Override
    public List<ClientDTO> findAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .toList();
    }

    @Override
    public ClientDTO findClientById(Long id) {
        return clientRepository.findById(id)
                .stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("[ClientServiceImpl::FindClientById] Client with id [{}] not found", id);
                    return new EntityNotFoundException(id);
                });
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO dto) {
        var existingClient = clientRepository.findById(id).orElseThrow(() -> {
            log.error("[ClientServiceImpl::UpdateClient] Client with id [{}] not found", id);
            return new EntityNotFoundException(id);
        });

        Client updatedClient = updateClientInfo(dto, existingClient);

        Client savedClient = clientRepository.save(updatedClient);

        return modelMapper.map(savedClient, ClientDTO.class);
    }

    @Override
    public void deleteClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            log.error("[Exception::ClientServiceImpl] Client with id [{}] not found", id);
            throw new EntityNotFoundException(id);
        }
        clientRepository.deleteById(client.get().getId());
    }

    private static Client updateClientInfo(ClientDTO dto, Client client) {
        return Client.builder()
                .id(client.getId())
                .firstName(checkForNullValue(dto.getFirstName(), client.getFirstName()))
                .lastName(checkForNullValue(dto.getLastName(), client.getLastName()))
                .email(checkForNullValue(dto.getEmail(), client.getEmail()))
                .phone(checkForNullValue(dto.getPhone(), client.getPhone()))
                .password(client.getPassword())
                .build();
    }

    private static String checkForNullValue(String input, String existingValue) {
        return input != null ? input : existingValue;
    }
}
