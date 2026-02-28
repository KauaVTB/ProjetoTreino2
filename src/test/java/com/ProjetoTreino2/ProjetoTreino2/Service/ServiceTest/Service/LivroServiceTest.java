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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

    List<LivroResponseDTO> livros;
    List<Cliente> clientes;

    @BeforeEach
    void setUp() {
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

        LivroDTO resultado = livroService.create(livro2);

        assertEquals("Ensaio sobre a Cegueira", resultado.getTitulo());
        verify(livroRepository, times(1)).save(any(Livro.class));
    }

    @Test
    void testfindById() {
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

        LivroResponseDTO resultado = livroService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Ensaio sobre a Cegueira", resultado.getTitulo());
    }

    @Test
    void testListarLivros() {
        Autor autor1 = new Autor(1L, "José Saramago", null);
        Autor autor2 = new Autor(2L, "Autor Desconhecido", null);
        Cliente cliente = new Cliente(1L, "João", "email", null);

        Livro livro1 = new Livro();
        livro1.setId(1L);
        livro1.setTitulo("Ensaio sobre a Cegueira");
        livro1.setAutor(autor1);
        livro1.setAnoPublicacao(1995);
        livro1.setClientes(List.of(cliente));

        Livro livro2 = new Livro();
        livro2.setId(2L);
        livro2.setTitulo("lijsdljfn");
        livro2.setAutor(autor2);
        livro2.setAnoPublicacao(2000);
        livro2.setClientes(List.of(cliente));

        when(livroRepository.findAll())
                .thenReturn(List.of(livro1, livro2));

        List<LivroResponseDTO> resultado = livroService.findAll();

        assertEquals(2, resultado.size());
    }

    @Test
    void testAtualizar() {
        Autor autor = new Autor(1L, "José Saramago", null);
        Cliente cliente = new Cliente(1L, "João", "email", null);

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Ensaio sobre a Cegueira");
        livro.setAutor(autor);
        livro.setAnoPublicacao(1995);
        livro.setClientes(List.of(cliente));

        LivroDTO livroDTO = new LivroDTO(1L, "Novo Título", 1L, 2000, List.of(1L));
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(clienteRepository.findAllById(List.of(1L))).thenReturn(List.of(cliente));
        when(livroRepository.save(any())).thenReturn(livro);

        LivroDTO resultado = livroService.update(1L, livroDTO);

        assertNotNull(resultado);
        verify(livroRepository, times(1)).save(any());
    }

    @Test
    void testDeletar() {
        livroService.delete(1L);
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

        Optional<Livro> opLivro = Optional.of(livro);
        when(livroRepository.findByTitulo("Ensaio sobre a Cegueira")).thenReturn(opLivro);

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

        when(livroRepository.findByAnoPublicacao(1995)).thenReturn(Optional.of(List.of(livro)));

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

    @Test
    void testFindByAutor() {
        Autor autor = new Autor(1L, "José Saramago", null);
        Cliente cliente = new Cliente(1L, "João", "email", null);

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Ensaio sobre a Cegueira");
        livro.setAutor(autor);
        livro.setAnoPublicacao(1995);
        livro.setClientes(List.of(cliente));

        when(livroRepository.findByAutorId(1L)).thenReturn(Optional.of(List.of(livro)));

        List<LivroResponseDTO> resultado = livroService.findByAutor(1L);

        assertNotNull(resultado);
        assertEquals("Ensaio sobre a Cegueira", resultado.get(0).getTitulo());
    }
}
