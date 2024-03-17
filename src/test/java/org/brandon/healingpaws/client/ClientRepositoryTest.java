package org.brandon.healingpaws.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("dev")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void test_Should_FindAll() {
        var client1 = Client.builder()
                .firstName("Brandon")
                .lastName("Bryan")
                .email("you@example.com")
                .phone("123-456-7890")
                .password("password")
                .build();

        var client2 = Client.builder()
                .firstName("Arantxa")
                .lastName("Leon")
                .email("arantxa@example.com")
                .phone("123-456-7890")
                .password("password")
                .build();

        clientRepository.saveAll(List.of(client1, client2));

        var clients = clientRepository.findAll();

        assertEquals(2, clients.size());
        assertTrue(clients.contains(client1));
        assertTrue(clients.contains(client2));
    }

    @Test
    void test_Should_FindById() {
        var client1 = Client.builder()
                .firstName("Brandon")
                .lastName("Bryan")
                .email("you@example.com")
                .phone("123-456-7890")
                .password("password")
                .build();

        var client2 = Client.builder()
                .firstName("Arantxa")
                .lastName("Leon")
                .email("arantxa@example.com")
                .phone("123-456-7890")
                .password("password")
                .build();

        clientRepository.saveAll(List.of(client1, client2));

        var foundClient = clientRepository.findById(1L).orElse(null);

        assertNotNull(foundClient);
        assertEquals(client1.getEmail(), foundClient.getEmail());
    }

    @Test
    void test_Should_Save() {
        var client = Client.builder()
                .firstName("Brandon")
                .lastName("Bryan")
                .email("you@example.com")
                .phone("123-456-7890")
                .password("password")
                .build();

        var savedClient = clientRepository.save(client);

        assertNotNull(savedClient);
        assertEquals(1, clientRepository.count());
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getId()).isGreaterThan(0L);
    }

    @Test
    void test_Should_Update() {
        var client = Client.builder()
                .firstName("Brandon")
                .lastName("Bryan")
                .email("you@example.com")
                .phone("123-456-7890")
                .password("password")
                .build();

        var savedClient = clientRepository.save(client);

        savedClient.setFirstName("Arantxa");
        savedClient.setLastName("Leon");

        var updatedClient = clientRepository.save(savedClient);

        assertNotNull(updatedClient);
        assertEquals(1, clientRepository.count());
        assertEquals("Arantxa", updatedClient.getFirstName());
        assertEquals("Leon", updatedClient.getLastName());
    }
}