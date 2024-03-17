package org.brandon.healingpaws.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.brandon.healingpaws.exceptions.ClientAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClientController.class)
@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ClientServiceImpl clientServiceImpl;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Client client;
    private ClientDTO clientDTO;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(1L)
                .firstName("Brandon")
                .lastName("Bryan")
                .email("brandon@example.com")
                .phone("123-456-7890")
                .password("password")
                .build();

        clientDTO = ClientDTO.builder()
                .firstName("Brandon")
                .lastName("Bryan")
                .email("brandon@example.com")
                .phone("123-456-7890")
                .build();
    }

    @Test
    void givenClient_whenSave_thenReturnClientDTOObject() throws Exception {
        when(clientServiceImpl.saveClient(client)).thenReturn(clientDTO);

        ResultActions response = mockMvc.perform(post("/api/v1/clients")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(client)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.first_name").value(clientDTO.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(clientDTO.getLastName()));
    }

    @Test
    void givenClients_whenFindAll_thenReturnListOfClientDTOObjects() throws Exception {
        when(clientServiceImpl.findAllClients()).thenReturn(List.of(clientDTO));

        ResultActions response = mockMvc.perform(get("/api/v1/clients"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].first_name").value(clientDTO.getFirstName()))
                .andExpect(jsonPath("$[0].last_name").value(clientDTO.getLastName()));
    }

    @Test
    void givenClientId_whenFindById_thenReturnClientDTOObject() throws Exception {
        when(clientServiceImpl.findClientById(1L)).thenReturn(clientDTO);

        ResultActions response = mockMvc.perform(get("/api/v1/clients/1"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.first_name").value(clientDTO.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(clientDTO.getLastName()));
    }

    @Test
    void givenClient_whenUpdate_thenReturnClientDTOObject() throws Exception {
        when(clientServiceImpl.updateClient(eq(client.getId()), any())).thenReturn(clientDTO);


        ResultActions response = mockMvc.perform(put("/api/v1/clients/{id}", client.getId())
                .accept("application/json")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.email").value(clientDTO.getEmail()));
    }

    @Test
    void givenClientId_whenDelete_thenReturnNoContent() throws Exception {
        ResultActions response = mockMvc.perform(delete("/api/v1/clients/1"));

        response.andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void givenNoClient_whenSave_thenReturnBadRequest() throws Exception {
        ResultActions response = mockMvc.perform(post("/api/v1/clients")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new Client())));

        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenClientWithNoEmail_whenSave_thenReturnBadRequest() throws Exception {
        client.setEmail(null);

        ResultActions response = mockMvc.perform(post("/api/v1/clients")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(client)));

        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenClientWithAlreadyExistingEmail_whenSave_thenReturnConflict() throws Exception {
        when(clientServiceImpl.saveClient(client)).thenThrow(new ClientAlreadyExistsException(client.getEmail()));

        ResultActions response = mockMvc.perform(post("/api/v1/clients")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(client)));

        response.andDo(print())
                .andExpect(status().isConflict());
    }
}