package org.brandon.healingpaws.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .password("password")
                .phone("5555555555")
                .build();
    }

    @AfterEach
    void tearDown() {
        clientRepository.deleteAll();
        client = null;
    }

    @DisplayName("Test for saveClient method")
    @Test
    void givenClientObject_whenSaveClient_thenReturnClientObject() {
        when(clientRepository.findByEmail(client.getEmail())).thenReturn(Optional.empty());
        when(clientRepository.save(client)).thenReturn(client);

        var savedClient = clientService.saveClient(client);

        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getId()).isGreaterThan(0L);
        assertEquals(savedClient.getEmail(), client.getEmail());
    }

    @DisplayName("Test for findClientById method")
    @Test
    void givenId_whenGetClientById_thenReturnClientObjectWithGivenId() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        var dbClient = clientService.findClientById(client.getId());

        assertThat(dbClient).isNotNull();
        assertThat(dbClient.getId()).isEqualTo(client.getId());
        assertEquals(dbClient.getEmail(), client.getEmail());
    }

    @DisplayName("Test for updateClient method")
    @Test
    void givenClientObject_whenUpdateClient_thenReturnUpdatedClientObject() {
        when(clientRepository.save(client)).thenReturn(client);
        client.setEmail("you@example.com");
        client.setFirstName("Bob");

        var updatedClient = clientService.updateClient(client.getId(), client);

        assertThat(updatedClient).isNotNull();
        assertEquals(updatedClient.getEmail(), "you@example.com");
        assertEquals(updatedClient.getFirstName(), "Bob");
        assertEquals(updatedClient.getLastName(), client.getLastName());
    }

    @DisplayName("Test for deleteClient method")
    @Test
    void givenClientObject_whenDeleteClient_thenNothing() {
        Long clientId = client.getId();

        doNothing().when(clientRepository).deleteById(clientId);

        clientService.deleteClient(clientId);

        var client = clientService.findClientById(clientId);

        assertThat(client).isNull();
        verify(clientRepository, times(1)).deleteById(clientId);
    }
}