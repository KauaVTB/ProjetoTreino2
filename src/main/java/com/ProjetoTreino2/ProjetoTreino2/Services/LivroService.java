package com.ProjetoTreino2.ProjetoTreino2.Services;

import com.ProjetoTreino2.ProjetoTreino2.Entities.Autor;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Livro;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import com.ProjetoTreino2.ProjetoTreino2.Repository.ClienteRepository;
import com.ProjetoTreino2.ProjetoTreino2.Repository.AutorRepository;
import com.ProjetoTreino2.ProjetoTreino2.Entities.Cliente;
import com.ProjetoTreino2.ProjetoTreino2.Repository.LivroRepository;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroResponseDTO;

@Service

public class LivroService {
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public String verificarDataPublicacao(int anoPublicacao) {
        if (anoPublicacao < 0 || anoPublicacao > java.time.Year.now().getValue()) {
            throw new RuntimeException("Ano de publicação inválido");
        }
        return "";
    }

    public LivroDTO Criar(LivroDTO livroDTO) {

        Autor autor = autorRepository
                .findById(livroDTO.getAutor_id())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(autor);
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());

        if (livroDTO.getCliente_id() != null && !livroDTO.getCliente_id().isEmpty()) {
            livro.setClientes(
                    clienteRepository.findAllById(livroDTO.getCliente_id()));
        }

        verificarDataPublicacao(livroDTO.getAnoPublicacao());
        Livro salvo = livroRepository.save(livro);

        return new LivroDTO(
                salvo.getId(),
                salvo.getTitulo(),
                salvo.getAutor().getId(),
                salvo.getAnoPublicacao(),
                salvo.getClientes().stream().map(Cliente::getId).toList());
    }

    public LivroResponseDTO FindById(@PathVariable Long id) {
        return livroRepository.findById(id).stream().findFirst().map(livro -> new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor().getNome(),
                livro.getAnoPublicacao(),
                livro.getClientes().stream().map(cliente -> cliente.getNome()).toList())).orElse(null);
    }

    public List<LivroResponseDTO> ListarLivros() {
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

    public LivroDTO Atualizar(Long id, LivroDTO livroDTO) {

        Livro livro = livroRepository.findById(id).orElseThrow();

        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(autorRepository.findById(livroDTO.getAutor_id()).orElseThrow());
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

    public void Deletar(@PathVariable Long id) {
        livroRepository.deleteById(id);
    }

    public LivroResponseDTO findByTitulo(String titulo) {
        Livro livro = livroRepository.findByTitulo(titulo);
        if (livro == null) {
            throw new RuntimeException("Livro não encontrado");
        }
        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor().getNome(),
                livro.getAnoPublicacao(),
                livro.getClientes().stream().map(cliente -> cliente.getNome()).toList());
    }

    public List<LivroResponseDTO> findByAnoPublicacao(int anoPublicacao) {
        List<Livro> livros = livroRepository.findByAnoPublicacao(anoPublicacao);
        if (livros.isEmpty()) {
            throw new RuntimeException("Nenhum livro encontrado para o ano de publicação: " + anoPublicacao);
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

    public List<LivroResponseDTO> findByAnoPublicacaoMaiorQue(Integer anoPublicacao) {
        List<Livro> livros = livroRepository.findByAnoPublicacaoMaiorQue(anoPublicacao);
        if (livros.isEmpty()) {
            throw new RuntimeException("Nenhum livro encontrado para o ano de publicação maior que: " + anoPublicacao);
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
            throw new RuntimeException("Nenhum livro encontrado para o ano de publicação menor que: " + anoPublicacao);
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
