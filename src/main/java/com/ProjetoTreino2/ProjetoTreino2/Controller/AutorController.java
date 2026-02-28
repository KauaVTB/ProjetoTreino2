package com.ProjetoTreino2.ProjetoTreino2.Controller;


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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/autores")
public class AutorController {
    
    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody AutorDTO autorDTO) {

            autorService.create(autorDTO);
            return ResponseEntity.ok().body("Autor criado com sucesso!");
       
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody AutorDTO autorDTO, @PathVariable Long id) {
        try {
            autorService.update(autorDTO, id);
            return ResponseEntity.ok().body("Autor atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar autor: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            autorService.delete(id);
            return ResponseEntity.ok().body("Autor deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao deletar autor: " + e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AutorResponseDTO>> findAll() {
        try {
            List<AutorResponseDTO> lista = this.autorService.findAll();
            return new ResponseEntity<>(lista, org.springframework.http.HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<AutorResponseDTO> findById(@PathVariable Long id) {
        
            AutorResponseDTO autor = autorService.findById(id);
            return ResponseEntity.ok().body(autor);
    }

}