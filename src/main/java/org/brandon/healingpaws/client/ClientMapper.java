package org.brandon.healingpaws.client;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class ClientMapper {

    public ClientDTO mapClientToDto(Client client) {
        return ClientDTO.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .build();
    }
}
