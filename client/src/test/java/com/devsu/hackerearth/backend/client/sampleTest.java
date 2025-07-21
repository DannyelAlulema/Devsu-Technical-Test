package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@ExtendWith(MockitoExtension.class)
public class sampleTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @Captor
    private ArgumentCaptor<ClientDto> clientDtoCaptor;

    @Captor
    private ArgumentCaptor<PartialClientDto> partialDtoCaptor;

    @BeforeEach
    void initRequestContext() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void getAllClientsTest() {
        // Arrange
        ClientDto client1 = new ClientDto();
        client1.setId(1L);
        client1.setDni("1726593104");
        client1.setName("Dannyel");
        client1.setPassword("PruebaDevsu2025");
        client1.setGender("M");
        client1.setAge(23);
        client1.setAddress("San Luis de Chillogallo - Quito, Ecuador");
        client1.setPhone("0996012875");
        client1.setActive(true);

        ClientDto client2 = new ClientDto();
        client2.setId(2L);
        client2.setDni("1726593105");
        client2.setName("Alejandro");
        client2.setPassword("PruebaDevsu2025");
        client2.setGender("M");
        client2.setAge(23);
        client2.setAddress("San Luis de Chillogallo - Quito, Ecuador");
        client2.setPhone("0996012875");
        client2.setActive(true);

        when(clientService.getAll()).thenReturn(List.of(client1, client2));

        // Act
        ResponseEntity<List<ClientDto>> response = clientController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().containsAll(List.of(client1, client2)));
        verify(clientService).getAll();
    }

    @Test
    void getClientByIdTest() {
        // Arrange
        ClientDto client = new ClientDto();
        client.setId(7L);
        client.setDni("1726593104");
        client.setName("Dannyel Alulema");
        client.setPassword("PruebaDevsu2025");
        client.setGender("M");
        client.setAge(23);
        client.setAddress("San Luis de Chillogallo - Quito, Ecuador");
        client.setPhone("0996012875");
        client.setActive(true);

        when(clientService.getById(client.getId())).thenReturn(client);

        // Act
        ResponseEntity<ClientDto> response = clientController.get(7L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
        verify(clientService).getById(7L);
    }

    @Test
    void createClientTest() {
        // Arrange
        ClientDto newClient = new ClientDto();
        newClient.setDni("1726593104");
        newClient.setName("Dannyel Alejandro Alulema");
        newClient.setPassword("PruebaDevsu2025");
        newClient.setGender("M");
        newClient.setAge(23);
        newClient.setAddress("San Luis de Chillogallo - Quito, Ecuador");
        newClient.setPhone("0996012875");
        newClient.setActive(true);

        ClientDto createdClient = new ClientDto();
        createdClient.setId(1L);
        createdClient.setDni(newClient.getDni());
        createdClient.setName(newClient.getName());
        createdClient.setPassword(newClient.getPassword());
        createdClient.setGender(newClient.getGender());
        createdClient.setAge(newClient.getAge());
        createdClient.setAddress(newClient.getAddress());
        createdClient.setPhone(newClient.getPhone());
        createdClient.setActive(newClient.isActive());

        when(clientService.create(newClient)).thenReturn(createdClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.create(newClient);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClient, response.getBody());

        verify(clientService).create(clientDtoCaptor.capture());
        assertEquals("Dannyel Alejandro Alulema", clientDtoCaptor.getValue().getName());
    }

    @Test
    void updateClientTest() {
        // Arrange
        ClientDto client = new ClientDto();
        client.setDni("1726593104");
        client.setName("Dannyel");
        client.setPassword("PruebaDevsu2025");
        client.setGender("M");
        client.setAge(23);
        client.setAddress("San Luis de Chillogallo - Quito, Ecuador");
        client.setPhone("0996012875");
        client.setActive(true);

        ClientDto clientUpdated = new ClientDto();
        clientUpdated.setId(3L);
        clientUpdated.setDni(client.getDni());
        clientUpdated.setName("Alejandro");
        clientUpdated.setPassword(client.getPassword());
        clientUpdated.setGender(client.getGender());
        clientUpdated.setAge(client.getAge());
        clientUpdated.setAddress(client.getAddress());
        clientUpdated.setPhone(client.getPhone());
        clientUpdated.setActive(client.isActive());

        when(clientService.update(eq(3L), any(ClientDto.class))).thenReturn(clientUpdated);

        // Act
        ResponseEntity<ClientDto> response = clientController.update(3L, clientUpdated);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientUpdated, response.getBody());
        verify(clientService).update(eq(3L), clientDtoCaptor.capture());
        assertEquals("Alejandro", clientDtoCaptor.getValue().getName());
    }

    @Test
    void partialUpdateClientTest() {
        // Arrange
        PartialClientDto patch = new PartialClientDto();
        patch.setActive(false);

        ClientDto afterPatch = new ClientDto();
        afterPatch.setId(5L);
        afterPatch.setDni("1726593104");
        afterPatch.setName("Dannyel Alulema");
        afterPatch.setPassword("PruebaDevsu2025");
        afterPatch.setGender("M");
        afterPatch.setAge(23);
        afterPatch.setAddress("San Luis de Chillogallo - Quito, Ecuador");
        afterPatch.setPhone("0996012875");
        afterPatch.setActive(false);

        when(clientService.partialUpdate(5L, patch)).thenReturn(afterPatch);

        // Act
        ResponseEntity<ClientDto> response = clientController.partialUpdate(5L, patch);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(afterPatch, response.getBody());
        verify(clientService).partialUpdate(eq(5L), partialDtoCaptor.capture());
        assertFalse(partialDtoCaptor.getValue().isActive());
    }

    @Test
    void deleteClientTest() {
        // Arrange
        doNothing().when(clientService).deleteById(9L);

        // Act
        ResponseEntity<Void> response = clientController.delete(9L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(clientService).deleteById(9L);
    }
}
