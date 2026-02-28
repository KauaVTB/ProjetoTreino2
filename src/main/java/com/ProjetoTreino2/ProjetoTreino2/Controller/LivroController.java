package com.ProjetoTreino2.ProjetoTreino2.Controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ProjetoTreino2.ProjetoTreino2.Exceptions.LivroNotFoundException;
import com.ProjetoTreino2.ProjetoTreino2.Services.LivroService;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.LivroResponseDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/api/livros")
public class LivroController {

    
    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody LivroDTO dto) {
        
            livroService.create(dto);
            return ResponseEntity.ok().body("Livro criado com sucesso!");}


    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @PathVariable Long id, @RequestBody LivroDTO dto) {
            livroService.update(id, dto);
            return ResponseEntity.ok().body("Livro atualizado com sucesso!");
        
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<LivroResponseDTO>> findAll() {
        try {
            return ResponseEntity.ok(livroService.findAll());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar livros: " + e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        
                try {
                    livroService.delete(id);
                    return ResponseEntity.ok().body("Livro deletado com sucesso!");
                } catch (Exception e) {
                    return ResponseEntity.status(500).body("Erro ao deletar livro: " + e.getMessage());
                }
        
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<LivroResponseDTO> findById(@PathVariable Long id) {
    
            LivroResponseDTO livro = livroService.findById(id);
            return ResponseEntity.ok(livro);
    }

    @GetMapping("/findByTitulo/{titulo}")
    public ResponseEntity<LivroResponseDTO> findByTitulo(@PathVariable String titulo) {

            LivroResponseDTO livro = livroService.findByTitulo(titulo);
            return ResponseEntity.ok(livro);
       
    }

    @GetMapping("/findByAnoPublicacao/{anoPublicacao}")
    public ResponseEntity<List<LivroResponseDTO>> findByAnoPublicacao(@PathVariable int anoPublicacao) {

            List<LivroResponseDTO> livros = livroService.findByAnoPublicacao(anoPublicacao);
            return ResponseEntity.ok(livros);
       
    }

    @GetMapping("/findByAnoPublicacaoMaiorQue/{anoPublicacao}")
    public ResponseEntity<List<LivroResponseDTO>> findByAnoPublicacaoMaiorQue(@PathVariable Integer anoPublicacao) {
        
            List<LivroResponseDTO> livros = livroService.findByAnoPublicacaoMaiorQue(anoPublicacao);
            return ResponseEntity.ok(livros);
        
    }

    @GetMapping("/findByAnoPublicacaoMenorQue/{anoPublicacao}")
    public ResponseEntity<List<LivroResponseDTO>> findByAnoPublicacaoMenorQue(@PathVariable Integer anoPublicacao) {
        
            List<LivroResponseDTO> livros = livroService.findByAnoPublicacaoMenorQue(anoPublicacao);
            return ResponseEntity.ok(livros);
        
    }

    @GetMapping("/findByAutor/{id}")
    public ResponseEntity<List<LivroResponseDTO>> findByAutor(@PathVariable long id) {
        
            List<LivroResponseDTO> livros = livroService.findByAutor(id);
            return ResponseEntity.ok(livros);
        
    }

}
