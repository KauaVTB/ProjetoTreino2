package com.ProjetoTreino2.ProjetoTreino2.Service.ServiceTest.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.ProjetoTreino2.ProjetoTreino2.Controller.LivroController;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Cliente;

import com.ProjetoTreino2.ProjetoTreino2.Repository.LivroRepository;
import com.ProjetoTreino2.ProjetoTreino2.Services.LivroService;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroResponseDTO;

@ExtendWith(MockitoExtension.class)
public class LivroControllerTest {

    @InjectMocks
    LivroController livroController;

    @Mock
    LivroRepository livroRepository;

    @Mock
    LivroService livroService;

    List<LivroResponseDTO> livros;

    LivroDTO livro = new LivroDTO(1L, "Livro 1", 1L, 2020, null);

    @BeforeEach
    public void setup() {
        livros = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1L, "Cliente 1", "sdofas", null));
        livros.add(
                new LivroResponseDTO(1L, "Livro 1", "Autor 1", 2020, clientes.stream().map(Cliente::getNome).toList()));
        livros.add(new LivroResponseDTO(2L, "Livro 2", "Autor 2", 2021, null));

    }

    @Test
    public void testListarLivros() {

        when(livroService.findAll()).thenReturn(livros);
        ResponseEntity<List<LivroResponseDTO>> response = livroController.findAll();

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testCriarLivro() {

        when(livroService.create(livro)).thenReturn(livro);
        ResponseEntity<String> response = livroController.create(livro);
        assertEquals("Livro criado com sucesso!", response.getBody());
    }

    @Test
    public void testAtualizarLivro() {

        when(livroService.update(1L, livro)).thenReturn(livro);
        ResponseEntity<String> response = livroController.update(1L, livro);
        assertEquals("Livro atualizado com sucesso!", response.getBody());
    }

    @Test
    public void testDeletarLivro() {
        ResponseEntity<String> response = livroController.delete(1L);
        assertEquals("Livro deletado com sucesso!", response.getBody());
    }

    @Test
    public void testFindById() {
        when(livroService.findById(1L)).thenReturn(livros.get(0));
        ResponseEntity<LivroResponseDTO> response = livroController.findById(1L);
        assertNotNull(response.getBody());
        assertEquals("Livro 1", response.getBody().getTitulo());
    }

    @Test
    public void testFindByTitulo() {
        when(livroService.findByTitulo("Livro 8")).thenReturn(livros.get(0));
        ResponseEntity<LivroResponseDTO> response = livroController.findByTitulo("Livro 8");
        assertNotNull(response.getBody());
        assertEquals("Livro 1", response.getBody().getTitulo());
    }

    @Test
    public void testFindByAnoPublicacao() {
        when(livroService.findByAnoPublicacao(2020)).thenReturn(livros);
        ResponseEntity<List<LivroResponseDTO>> response = livroController.findByAnoPublicacao(2020);
        assertNotNull(response.getBody());
        assertEquals("Livro 1", response.getBody().get(0).getTitulo());
    }
}
