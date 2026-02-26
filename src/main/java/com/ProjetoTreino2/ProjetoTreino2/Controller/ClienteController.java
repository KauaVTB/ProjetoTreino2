package com.ProjetoTreino2.ProjetoTreino2.Controller;

import com.ProjetoTreino2.ProjetoTreino2.Services.ClienteService;
import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.ClienteResponseDTO;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/criar")
    public ResponseEntity<String> postMethodName(@RequestBody ClienteDTO clientedto) {
        try {
            clienteService.CriarCliente(clientedto);
            return ResponseEntity.ok("Cliente criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar cliente: " + e.getMessage());
        }

    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizar(@RequestBody ClienteDTO clienteDTO, @PathVariable Long id) {
        try {
            clienteService.Atualizar(clienteDTO, id);
            return ResponseEntity.ok("Cliente atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar cliente: " + e.getMessage());
        }

    }

    @GetMapping("/listar")
    public ResponseEntity<List<ClienteResponseDTO>> ListarClientes() {
        try {
            List<ClienteResponseDTO> lista = this.clienteService.ListarClientes();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            clienteService.Deletar(id);
            return ResponseEntity.ok("Cliente deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id) {
        try {
            ClienteResponseDTO cliente = clienteService.FindById(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

}
