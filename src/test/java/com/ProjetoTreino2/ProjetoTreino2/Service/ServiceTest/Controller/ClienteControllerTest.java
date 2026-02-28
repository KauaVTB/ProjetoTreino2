package com.ProjetoTreino2.ProjetoTreino2.Service.ServiceTest.Controller;

import com.ProjetoTreino2.ProjetoTreino2.Controller.ClienteController;
import com.ProjetoTreino2.ProjetoTreino2.Services.ClienteService;
import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private ClienteDTO clienteDTO;
    private ClienteResponseDTO clienteResponseDTO;

    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setNome("João Silva");
        clienteDTO.setEmail("joao@email.com");

        clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setId(1L);
        clienteResponseDTO.setNome("João Silva");
        clienteResponseDTO.setEmail("joao@email.com");
    }

    @Test
    void testCreate_Success() {
        ResponseEntity<String> response = clienteController.create(clienteDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente criado com sucesso!", response.getBody());
        verify(clienteService, times(1)).create(clienteDTO);
    }

    @Test
    void testCreate_Error() {
        doThrow(new RuntimeException("Erro na criação")).when(clienteService).create(any());

        ResponseEntity<String> response = clienteController.create(clienteDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Erro na criação"));
    }

    @Test
    void testAtualizar_Success() {
        ResponseEntity<String> response = clienteController.update(clienteDTO, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente atualizado com sucesso!", response.getBody());
        verify(clienteService, times(1)).update(clienteDTO, 1L);
    }

    @Test
    void testAtualizar_Error() {
        doThrow(new RuntimeException("Erro na atualização")).when(clienteService).update(any(), anyLong());
        ResponseEntity<String> response = clienteController.update(clienteDTO, 1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Erro ao atualizar cliente"));
    }

    @Test
    void testListarClientes_Success() {
        List<ClienteResponseDTO> listaMock = Arrays.asList(clienteResponseDTO);
        when(clienteService.findAll()).thenReturn(listaMock);
        ResponseEntity<List<ClienteResponseDTO>> response = clienteController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(clienteService, times(1)).findAll();
    }

    @Test
    void testListarClientes_Error() {
        when(clienteService.findAll()).thenThrow(new RuntimeException("Erro ao listar"));
        ResponseEntity<List<ClienteResponseDTO>> response = clienteController.findAll();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeletar_Success() {
        ResponseEntity<String> response = clienteController.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente deletado com sucesso!", response.getBody());
        verify(clienteService, times(1)).delete(1L);
    }

    @Test
    void testDeletar_Error() {
        doThrow(new RuntimeException("Erro ao deletar")).when(clienteService).delete(anyLong());
        ResponseEntity<String> response = clienteController.delete(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Erro ao deletar cliente"));
    }

    @Test
    void testFindById_Success() {
        when(clienteService.findById(1L)).thenReturn(clienteResponseDTO);
        ResponseEntity<ClienteResponseDTO> response = clienteController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("João Silva", response.getBody().getNome());
        verify(clienteService, times(1)).findById(1L);
    }

    @Test
    void testFindById_Error() {
        when(clienteService.findById(anyLong())).thenThrow(new RuntimeException("Cliente não encontrado"));
        ResponseEntity<ClienteResponseDTO> response = clienteController.findById(999L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}