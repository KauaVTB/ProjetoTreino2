package com.ProjetoTreino2.ProjetoTreino2.Service.ServiceTest.Controller;

import com.ProjetoTreino2.ProjetoTreino2.Controller.ClienteController;
import com.ProjetoTreino2.ProjetoTreino2.Services.ClienteService;
import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private ClienteDTO clienteDTO;
    private ClienteResponseDTO clienteResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteDTO = new ClienteDTO();
        clienteDTO.setNome("João Silva");
        clienteDTO.setEmail("joao@email.com");

        clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setId(1L);
        clienteResponseDTO.setNome("João Silva");
        clienteResponseDTO.setEmail("joao@email.com");
    }

    @Test
    void testPostMethodName_Success() {
        ResponseEntity<String> response = clienteController.postMethodName(clienteDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente criado com sucesso!", response.getBody());
        verify(clienteService, times(1)).CriarCliente(clienteDTO);
    }

    @Test
    void testPostMethodName_Error() {
        doThrow(new RuntimeException("Erro na criação")).when(clienteService).CriarCliente(any());
        ResponseEntity<String> response = clienteController.postMethodName(clienteDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Erro ao criar cliente"));
    }

    @Test
    void testAtualizar_Success() {
        ResponseEntity<String> response = clienteController.atualizar(clienteDTO, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente atualizado com sucesso!", response.getBody());
        verify(clienteService, times(1)).Atualizar(clienteDTO, 1L);
    }

    @Test
    void testAtualizar_Error() {
        doThrow(new RuntimeException("Erro na atualização")).when(clienteService).Atualizar(any(), anyLong());
        ResponseEntity<String> response = clienteController.atualizar(clienteDTO, 1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Erro ao atualizar cliente"));
    }

    @Test
    void testListarClientes_Success() {
        List<ClienteResponseDTO> listaMock = Arrays.asList(clienteResponseDTO);
        when(clienteService.ListarClientes()).thenReturn(listaMock);
        ResponseEntity<List<ClienteResponseDTO>> response = clienteController.ListarClientes();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(clienteService, times(1)).ListarClientes();
    }

    @Test
    void testListarClientes_Error() {
        when(clienteService.ListarClientes()).thenThrow(new RuntimeException("Erro ao listar"));
        ResponseEntity<List<ClienteResponseDTO>> response = clienteController.ListarClientes();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeletar_Success() {
        ResponseEntity<String> response = clienteController.deletar(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente deletado com sucesso!", response.getBody());
        verify(clienteService, times(1)).Deletar(1L);
    }

    @Test
    void testDeletar_Error() {
        doThrow(new RuntimeException("Erro ao deletar")).when(clienteService).Deletar(anyLong());
        ResponseEntity<String> response = clienteController.deletar(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Erro ao deletar cliente"));
    }

    @Test
    void testFindById_Success() {
        when(clienteService.FindById(1L)).thenReturn(clienteResponseDTO);
        ResponseEntity<ClienteResponseDTO> response = clienteController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("João Silva", response.getBody().getNome());
        verify(clienteService, times(1)).FindById(1L);
    }

    @Test
    void testFindById_Error() {
        when(clienteService.FindById(anyLong())).thenThrow(new RuntimeException("Cliente não encontrado"));
        ResponseEntity<ClienteResponseDTO> response = clienteController.findById(999L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}