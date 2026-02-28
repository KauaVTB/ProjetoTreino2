package com.ProjetoTreino2.ProjetoTreino2.Services;

import com.ProjetoTreino2.ProjetoTreino2.Entities.Autor;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Livro;
import com.ProjetoTreino2.ProjetoTreino2.Exceptions.*;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroDTO;

import org.springframework.stereotype.Service;

import java.util.List;
import com.ProjetoTreino2.ProjetoTreino2.Repository.ClienteRepository;
import com.ProjetoTreino2.ProjetoTreino2.Repository.AutorRepository;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Cliente;
import com.ProjetoTreino2.ProjetoTreino2.Repository.LivroRepository;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroResponseDTO;

@Service

public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final ClienteRepository clienteRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository,
            ClienteRepository clienteRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.clienteRepository = clienteRepository;
    }

    public String verifyPublicationDate(int anoPublicacao) {
        if (anoPublicacao < 0 || anoPublicacao > java.time.Year.now().getValue()) {
            throw new AnoPublicacaoInvalidoException();
        }
        return "";
    }

    public LivroDTO create(LivroDTO livroDTO) {

        Autor autor = autorRepository
                .findById(livroDTO.getAutor_id())
                .orElseThrow(() -> new AutorNotFoundException());

        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(autor);
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());

        if (livroDTO.getCliente_id() != null && !livroDTO.getCliente_id().isEmpty()) {
            livro.setClientes(
                    clienteRepository.findAllById(livroDTO.getCliente_id()));
        } 
        verifyPublicationDate(livroDTO.getAnoPublicacao());
        Livro salvo = livroRepository.save(livro);

        return new LivroDTO(
                salvo.getId(),
                salvo.getTitulo(),
                salvo.getAutor().getId(),
                salvo.getAnoPublicacao(),
                salvo.getClientes().stream().map(Cliente::getId).toList());
    }

    public LivroResponseDTO findById(Long id) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new LivroNotFoundException());
        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor().getNome(),
                livro.getAnoPublicacao(),
                livro.getClientes().stream().map(cliente -> cliente.getNome()).toList());
    }

    public List<LivroResponseDTO> findAll() {
        return livroRepository.findAll()
                .stream()
                .map(livro -> new LivroResponseDTO(
                        livro.getId(),
                        livro.getTitulo(),
                        livro.getAutor().getNome(),
                        livro.getAnoPublicacao(),
                        livro.getClientes().stream().map(cliente -> cliente.getNome()).toList()))
                .toList();
    }

    public LivroDTO update(Long id, LivroDTO livroDTO) {

        Livro livro = livroRepository.findById(id).orElseThrow(() -> new LivroNotFoundException());

        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(autorRepository.findById(livroDTO.getAutor_id()).orElseThrow(() -> new AutorNotFoundException()));
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
        livro.setClientes(clienteRepository.findAllById(livroDTO.getCliente_id()));

        Livro salvo = livroRepository.save(livro);

        return new LivroDTO(
                salvo.getId(),
                salvo.getTitulo(),
                salvo.getAutor().getId(),
                salvo.getAnoPublicacao(),
                salvo.getClientes().stream().map(Cliente::getId).toList());
    }

    public void delete(Long id) {
        livroRepository.findById(id).orElseThrow(() -> new LivroNotFoundException());
        livroRepository.deleteById(id);
    }

    public LivroResponseDTO findByTitulo(String titulo) {
        Livro livro = livroRepository.findByTitulo(titulo).orElseThrow(() -> new LivroNotFoundException());
        
        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor().getNome(),
                livro.getAnoPublicacao(),
                livro.getClientes().stream().map(cliente -> cliente.getNome()).toList());
    }

    public List<LivroResponseDTO> findByAnoPublicacao(int anoPublicacao) {
        
        List<Livro> livros = livroRepository.findByAnoPublicacao(anoPublicacao).orElse(null);
        if (livros.isEmpty()) {
            throw new LivroNotFoundException();
        }
        
        return livros.stream()
                .map(livro -> new LivroResponseDTO(
                        livro.getId(),
                        livro.getTitulo(),
                        livro.getAutor().getNome(),
                        livro.getAnoPublicacao(),
                        livro.getClientes().stream().map(cliente -> cliente.getNome()).toList()))
                .toList();
    }


 
    public List<LivroResponseDTO> findByAutor(long id) {
        List<Livro> livros = livroRepository.findByAutorId(id).orElseThrow(() -> new LivroNotFoundException());
        
        return livros.stream()
                .map(livro -> new LivroResponseDTO(
                        livro.getId(),
                        livro.getTitulo(),
                        livro.getAutor().getNome(),
                        livro.getAnoPublicacao(),
                        livro.getClientes().stream().map(cliente -> cliente.getNome()).toList()))
                .toList();
    }

    public List<LivroResponseDTO> findByAnoPublicacaoMaiorQue(Integer anoPublicacao) {
        List<Livro> livros = livroRepository.findByAnoPublicacaoMaiorQue(anoPublicacao);
        if (livros.isEmpty()) {
            throw new LivroNotFoundException();
        }
        return livros.stream()
                .map(livro -> new LivroResponseDTO(
                        livro.getId(),
                        livro.getTitulo(),
                        livro.getAutor().getNome(),
                        livro.getAnoPublicacao(),
                        livro.getClientes().stream().map(cliente -> cliente.getNome()).toList()))
                .toList();
    }

    public List<LivroResponseDTO> findByAnoPublicacaoMenorQue(Integer anoPublicacao) {
        List<Livro> livros = livroRepository.findByAnoPublicacaoMenorQue(anoPublicacao);
        if (livros.isEmpty()) {
            throw new LivroNotFoundException();
        }
        return livros.stream()
                .map(livro -> new LivroResponseDTO(
                        livro.getId(),
                        livro.getTitulo(),
                        livro.getAutor().getNome(),
                        livro.getAnoPublicacao(),
                        livro.getClientes().stream().map(cliente -> cliente.getNome()).toList()))
                .toList();
    }

}
