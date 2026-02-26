package com.ProjetoTreino2.ProjetoTreino2.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.ProjetoTreino2.ProjetoTreino2.Services.AutorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import com.ProjetoTreino2.ProjetoTreino2.dto.AutorDTO;
import org.springframework.web.bind.annotation.PathVariable;
import com.ProjetoTreino2.ProjetoTreino2.dto.AutorResponseDTO;

@RestController
@RequestMapping("/api/autores")
public class AutorController {
    @Autowired
    AutorService autorService;

    @PostMapping("/criar")
    public ResponseEntity<String> criar(@RequestBody AutorDTO autorDTO) {
        try {
            autorService.CriarAutor(autorDTO);
            return ResponseEntity.ok().body("Autor criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar autor: " + e.getMessage());
        }
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<String> atualizar(@RequestBody AutorDTO autorDTO, @PathVariable Long id) {
        try {
            autorService.Atualizar(autorDTO, id);
            return ResponseEntity.ok().body("Autor atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar autor: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            autorService.Deletar(id);
            return ResponseEntity.ok().body("Autor deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao deletar autor: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<AutorResponseDTO>> listarAutores() {
        try {
            List<AutorResponseDTO> lista = this.autorService.listarAutores();
            return new ResponseEntity<>(lista, org.springframework.http.HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<AutorResponseDTO> findById(@PathVariable Long id) {
        try {
            AutorResponseDTO autor = autorService.findById(id);
            return ResponseEntity.ok().body(autor);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}