package org.brandon.healingpaws.client;

import org.brandon.healingpaws.exceptions.ClientAlreadyExistsException;
import org.brandon.healingpaws.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenClient_whenSaveClient_thenClientSavedSuccessfully() {
        var client = Client.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9545403600")
                .password("password")
                .build();

        var clientDTO = ClientDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9545403600")
                .build();

        when(clientRepository.existsByEmail(client.getEmail())).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(modelMapper.map(client, ClientDTO.class)).thenReturn(clientDTO);

        ClientDTO savedClientDTO = clientServiceImpl.saveClient(client);

        assertNotNull(savedClientDTO);
        assertEquals(clientDTO.getFirstName(), savedClientDTO.getFirstName());
        assertEquals(clientDTO.getLastName(), savedClientDTO.getLastName());
        assertEquals(clientDTO.getEmail(), savedClientDTO.getEmail());
        assertEquals(clientDTO.getPhone(), savedClientDTO.getPhone());
    }

    @Test
    void givenClientId_whenFindClientById_thenClientFoundSuccessfully() {
        Long id = 1L;

        var client = Client.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9545403600")
                .password("password")
                .build();

        var clientDTO = ClientDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9545403600")
                .build();

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(modelMapper.map(client, ClientDTO.class)).thenReturn(clientDTO);

        ClientDTO foundClientDTO = clientServiceImpl.findClientById(id);

        assertNotNull(foundClientDTO);
        assertEquals(clientDTO.getFirstName(), foundClientDTO.getFirstName());
        assertEquals(clientDTO.getLastName(), foundClientDTO.getLastName());
        assertEquals(clientDTO.getEmail(), foundClientDTO.getEmail());
        assertEquals(clientDTO.getPhone(), foundClientDTO.getPhone());

        verify(clientRepository, times(1)).findById(id);
        verify(modelMapper, times(1)).map(client, ClientDTO.class);
    }

    @Test
    void givenNoParameters_whenFindAllClients_thenAllClientsFoundSuccessfully() {
        var client1 = Client.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9545403600")
                .password("password")
                .build();

        var client2 = Client.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("1234567890")
                .password("password")
                .build();

        var clientDTO1 = ClientDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9545403600")
                .build();

        var clientDTO2 = ClientDTO.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("1234567890")
                .build();

        List<Client> clients = Arrays.asList(client1, client2);
        List<ClientDTO> clientDTOs = Arrays.asList(clientDTO1, clientDTO2);

        when(clientRepository.findAll()).thenReturn(clients);
        when(modelMapper.map(client1, ClientDTO.class)).thenReturn(clientDTO1);
        when(modelMapper.map(client2, ClientDTO.class)).thenReturn(clientDTO2);

        List<ClientDTO> foundClientDTOs = clientServiceImpl.findAllClients();

        assertNotNull(foundClientDTOs);
        assertEquals(clientDTOs.size(), foundClientDTOs.size());
        assertEquals(clientDTOs.getFirst().getFirstName(), foundClientDTOs.getFirst().getFirstName());
        assertEquals(clientDTOs.getFirst().getLastName(), foundClientDTOs.getFirst().getLastName());
        assertEquals(clientDTOs.get(0).getEmail(), foundClientDTOs.get(0).getEmail());
        assertEquals(clientDTOs.get(0).getPhone(), foundClientDTOs.get(0).getPhone());
        assertEquals(clientDTOs.get(1).getFirstName(), foundClientDTOs.get(1).getFirstName());
        assertEquals(clientDTOs.get(1).getLastName(), foundClientDTOs.get(1).getLastName());
        assertEquals(clientDTOs.get(1).getEmail(), foundClientDTOs.get(1).getEmail());
        assertEquals(clientDTOs.get(1).getPhone(), foundClientDTOs.get(1).getPhone());
    }

    @Test
    void givenClientId_whenUpdateClient_thenClientUpdateSuccessfully() {
        Long id = 1L;

        var client = Client.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9545403600")
                .password("password")
                .build();

        var updatedClient = Client.builder()
                .id(id)
                .firstName("Updated John")
                .lastName("Updated Doe")
                .email("updatedjohn@example.com")
                .phone("1234567890")
                .password("updatedpassword")
                .build();

        var clientDTO = ClientDTO.builder()
                .firstName("Updated John")
                .lastName("Updated Doe")
                .email("updatedjohn@example.com")
                .phone("1234567890")
                .build();

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);
        when(modelMapper.map(updatedClient, ClientDTO.class)).thenReturn(clientDTO);

        ClientDTO updatedClientDTO = clientServiceImpl.updateClient(id, clientDTO);

        assertNotNull(updatedClientDTO);
        assertEquals(clientDTO.getFirstName(), updatedClientDTO.getFirstName());
        assertEquals(clientDTO.getLastName(), updatedClientDTO.getLastName());
        assertEquals(clientDTO.getEmail(), updatedClientDTO.getEmail());
        assertEquals(clientDTO.getPhone(), updatedClientDTO.getPhone());
    }

    @Test
    void givenClientId_whenDeleteClient_thenClientDeletedSuccessfully() {
        Long id = 1L;
        var client = Client.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9545403600")
                .password("password")
                .build();

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        clientServiceImpl.deleteClient(client.getId());

        verify(clientRepository, times(1)).deleteById(id);
    }

    @Test
    void givenInvalidId_whenFindById_thenThrowEntityNotFoundException() {
        Long id = 0L;

        when(clientRepository.findById(id)).thenThrow(new EntityNotFoundException(id));

        var entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> clientServiceImpl.findClientById(id));

        assertEquals(entityNotFoundException.getMessage(), "Client with id [0] not found");

        verify(clientRepository, times(1)).findById(id);
    }

    @Test
    void givenAlreadyExistingClient_whenSaveClient_throwClientAlreadyExistsException() {
        var existingClient = Client.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("9545403600")
                .password("password")
                .build();

        when(clientRepository.existsByEmail(existingClient.getEmail())).thenReturn(true);
        when(clientRepository.save(existingClient)).thenThrow(new ClientAlreadyExistsException(existingClient.getEmail()));

        var clientAlreadyExistsException = assertThrows(ClientAlreadyExistsException.class, () -> clientServiceImpl.saveClient(existingClient));

        assertEquals(clientAlreadyExistsException.getMessage(), String.format("Client with email [%s] already exists", existingClient.getEmail()));
    }
}