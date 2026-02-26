package com.ProjetoTreino2.ProjetoTreino2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ProjetoTreino2.ProjetoTreino2.Services.LivroService;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    LivroService livroService;

    @PostMapping("/criar")
    public ResponseEntity<String> criar(@RequestBody LivroDTO dto) {
        try {
            livroService.Criar(dto);
            return ResponseEntity.ok().body("Livro criado com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar livro: " + e.getMessage());
        }

    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody LivroDTO dto) {
        try {
            livroService.Atualizar(id, dto);
            return ResponseEntity.ok().body("Livro atualizado com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<LivroResponseDTO>> ListarLivros() {
        try {
            return ResponseEntity.ok(livroService.ListarLivros());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar livros: " + e.getMessage());
        }

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> Deletar(@PathVariable Long id) {
        try {
            livroService.Deletar(id);
            return ResponseEntity.ok().body("Livro deletado com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar livro: " + e.getMessage());
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<LivroResponseDTO> FindById(@PathVariable Long id) {
        try {
            LivroResponseDTO livro = livroService.FindById(id);
            return ResponseEntity.ok(livro);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao encontrar livro: " + e.getMessage());
        }
    }

    @GetMapping("/findByTitulo/{titulo}")
    public ResponseEntity<LivroResponseDTO> findByTitulo(@PathVariable String titulo) {
        try {
            LivroResponseDTO livro = livroService.findByTitulo(titulo);
            return ResponseEntity.ok(livro);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao encontrar livro por título: " + e.getMessage());
        }
    }

    @GetMapping("/findByAnoPublicacao/{anoPublicacao}")
    public ResponseEntity<List<LivroResponseDTO>> findByAnoPublicacao(@PathVariable int anoPublicacao) {
        try {
            List<LivroResponseDTO> livros = livroService.findByAnoPublicacao(anoPublicacao);
            return ResponseEntity.ok(livros);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao encontrar livros por ano de publicação: " + e.getMessage());
        }
    }

    @GetMapping("/findByAnoPublicacaoMaiorQue/{anoPublicacao}")
    public ResponseEntity<List<LivroResponseDTO>> findByAnoPublicacaoMaiorQue(@PathVariable Integer anoPublicacao) {
        try {
            List<LivroResponseDTO> livros = livroService.findByAnoPublicacaoMaiorQue(anoPublicacao);
            return ResponseEntity.ok(livros);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao encontrar livros por ano de publicação maior que: " + e.getMessage());
        }
    }

}
