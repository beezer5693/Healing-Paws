package org.brandon.healingpaws.client;

import java.util.List;

public interface ClientService {
    ClientDTO saveClient(Client client);

    List<ClientDTO> findAllClients();

    ClientDTO findClientById(Long id);

    ClientDTO updateClient(Long id, ClientDTO dto);

    void deleteClient(Long id);
}
