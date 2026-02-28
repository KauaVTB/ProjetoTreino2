package com.ProjetoTreino2.ProjetoTreino2.Controller;

import com.ProjetoTreino2.ProjetoTreino2.Services.ClienteService;
import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteResponseDTO;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody ClienteDTO clientedto) {

            clienteService.create(clientedto);
            return ResponseEntity.ok("Cliente criado com sucesso!");

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Long id) {
            clienteService.update(clienteDTO, id);
            return ResponseEntity.ok("Cliente atualizado com sucesso!");
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ClienteResponseDTO>> findAll() {
        try {
            List<ClienteResponseDTO> lista = this.clienteService.findAll();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
            clienteService.delete(id);
            return ResponseEntity.ok("Cliente deletado com sucesso!");
        
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id) {

            ClienteResponseDTO cliente = clienteService.findById(id);
            return ResponseEntity.ok(cliente);
        

    }

}
