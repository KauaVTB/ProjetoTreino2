package com.ProjetoTreino2.ProjetoTreino2.Service.ServiceTest.Service;

import com.ProjetoTreino2.ProjetoTreino2.Entities.Autor;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Cliente;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Livro;
import com.ProjetoTreino2.ProjetoTreino2.Repository.AutorRepository;
import com.ProjetoTreino2.ProjetoTreino2.Repository.ClienteRepository;
import com.ProjetoTreino2.ProjetoTreino2.Repository.LivroRepository;
import com.ProjetoTreino2.ProjetoTreino2.Services.LivroService;
import com.ProjetoTreino2.ProjetoTreino2.dto.AutorDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LivroServiceTest {
    @Mock
    private LivroRepository livroRepository;
    @Mock
    private AutorRepository autorRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private LivroService livroService;

    private LivroDTO livroDTO;
    private AutorDTO autor;
    private ClienteDTO cliente;
    private Livro livro;

    List<LivroResponseDTO> livros;
    List<Cliente> clientes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        autor = new AutorDTO();
        autor.setId(1L);
        autor.setNome("José Saramago");
        cliente = new ClienteDTO();
        cliente.setId(1L);
        cliente.setNome("João");
        livroDTO = new LivroDTO();
        livroDTO.setId(1L);
        livroDTO.setTitulo("Ensaio sobre a Cegueira");
        livroDTO.setAutor_id(autor.getId());
        livroDTO.setAnoPublicacao(1995);
        livroDTO.setCliente_id(List.of(cliente.getId()));

        livros = List.of(new LivroResponseDTO(1L, "Ensaio sobre a Cegueira", "José Saramago", 1995, List.of("João")));
        clientes = List.of(new Cliente(1L, "João", "joao@email.com", null));
        List<LivroResponseDTO> lista = new ArrayList<>();
        lista.add(new LivroResponseDTO(1L, "Ensaio sobre a Cegueira", "José Saramago", 1995, List.of("João")));
        lista.add(new LivroResponseDTO(2L, "lijsdljfn", "Autor Desconhecido", 2000, List.of("João")));
    }

    @Test
    void testCriar() {

        Autor autor = new Autor(1L, "José Saramago", null);

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Ensaio sobre a Cegueira");
        livro.setAutor(autor);
        livro.setAnoPublicacao(1995);
        livro.setClientes(clientes);

        LivroDTO livro2 = new LivroDTO(1L, "Ensaio sobre a Cegueira", 1L, 1995, List.of(1L));

        when(autorRepository.findById(1L))
                .thenReturn(Optional.of(autor));

        when(clienteRepository.findAllById(List.of(1L)))
                .thenReturn(clientes);

        when(livroRepository.save(any(Livro.class)))
                .thenReturn(livro);

        LivroDTO resultado = livroService.Criar(livro2);

        assertEquals("Ensaio sobre a Cegueira", resultado.getTitulo());
        verify(livroRepository, times(1)).save(any(Livro.class));
    }

    @Test
    void testFindById() {
        Autor autor = new Autor(1L, "José Saramago", null);
        Cliente cliente = new Cliente(1L, "João", "email", null);

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Ensaio sobre a Cegueira");
        livro.setAutor(autor);
        livro.setAnoPublicacao(1995);
        livro.setClientes(List.of(cliente));

        when(livroRepository.findById(1L))
                .thenReturn(Optional.of(livro));

        LivroResponseDTO resultado = livroService.FindById(1L);

        assertNotNull(resultado);
        assertEquals("Ensaio sobre a Cegueira", resultado.getTitulo());
    }

    @Test
    void testListarLivros() {
        List<LivroResponseDTO> lista = new ArrayList<>();
        lista.add(new LivroResponseDTO(1L, "Ensaio sobre a Cegueira", "José Saramago", 1995, List.of("João")));
        lista.add(new LivroResponseDTO(2L, "lijsdljfn", "Autor Desconhecido", 2000, List.of("João")));

        when(livroRepository.findAll())
                .thenReturn(List.of(new Livro(), new Livro()));

        List<LivroResponseDTO> resultado = livroService.ListarLivros();

        assertEquals(2, resultado.size());
    }

    @Test
    void testAtualizar() {
        LivroDTO livroDTO = new LivroDTO(1L, "Novo Título", 1L, 2000, List.of(1L));
        when(livroRepository.findById(1L)).thenReturn(livroRepository.findById(1L));
        when(autorRepository.findById(1L)).thenReturn(autorRepository.findById(1L));
        when(clienteRepository.findAllById(List.of(1L))).thenReturn(clienteRepository.findAllById(List.of(1L)));
        when(livroRepository.save(any())).thenReturn(livro);

        LivroDTO resultado = livroService.Atualizar(1L, livroDTO);

        assertNotNull(resultado);
        verify(livroRepository, times(1)).save(any());
    }

    @Test
    void testDeletar() {
        livroService.Deletar(1L);
        verify(livroRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByTitulo() {
        Autor autor = new Autor(1L, "José Saramago", null);
        Cliente cliente = new Cliente(1L, "João", "email", null);

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Ensaio sobre a Cegueira");
        livro.setAutor(autor);
        livro.setAnoPublicacao(1995);
        livro.setClientes(List.of(cliente));

        when(livroRepository.findByTitulo("Ensaio sobre a Cegueira")).thenReturn(livro);

        LivroResponseDTO resultado = livroService.findByTitulo("Ensaio sobre a Cegueira");

        assertNotNull(resultado);
        assertEquals("Ensaio sobre a Cegueira", resultado.getTitulo());
    }

    @Test
    void testFindByAnoPublicacao() {
        Autor autor = new Autor(1L, "José Saramago", null);
        Cliente cliente = new Cliente(1L, "João", "email", null);

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Ensaio sobre a Cegueira");
        livro.setAutor(autor);
        livro.setAnoPublicacao(1995);
        livro.setClientes(List.of(cliente));

        when(livroRepository.findByAnoPublicacao(1995)).thenReturn(List.of(livro));

        List<LivroResponseDTO> resultado = livroService.findByAnoPublicacao(1995);

        assertEquals(1, resultado.size());
    }

    @Test
    void testFindByAnoPublicacaoMaiorQue() {

        Autor autor = new Autor(1L, "José Saramago", null);
        Cliente cliente = new Cliente(1L, "João", "email", null);

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Ensaio sobre a Cegueira");
        livro.setAutor(autor);
        livro.setAnoPublicacao(1995);
        livro.setClientes(List.of(cliente));

        when(livroRepository.findByAnoPublicacaoMaiorQue(1990)).thenReturn(List.of(livro));

        List<LivroResponseDTO> resultado = livroService.findByAnoPublicacaoMaiorQue(1990);

        assertEquals(1, resultado.size());
    }

    @Test
    void testFindByAnoPublicacaoMenorQue() {

        Autor autor = new Autor(1L, "José Saramago", null);
        Cliente cliente = new Cliente(1L, "João", "email", null);

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Ensaio sobre a Cegueira");
        livro.setAutor(autor);
        livro.setAnoPublicacao(1995);
        livro.setClientes(List.of(cliente));

        when(livroRepository.findByAnoPublicacaoMenorQue(2000)).thenReturn(List.of(livro));

        List<LivroResponseDTO> resultado = livroService.findByAnoPublicacaoMenorQue(2000);

        assertEquals(1, resultado.size());
    }
}
