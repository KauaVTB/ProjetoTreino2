package com.ProjetoTreino2.ProjetoTreino2.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ProjetoTreino2.ProjetoTreino2.Entities.User;
import com.ProjetoTreino2.ProjetoTreino2.Exceptions.UserNotFoundException;
import com.ProjetoTreino2.ProjetoTreino2.Services.UserService;
import com.ProjetoTreino2.ProjetoTreino2.dto.UserResponseDTO;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/api/usuarios")
public class UserController {
    
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
      return ResponseEntity.ok(userService.findAll());
         
    }

    @GetMapping("/findByLogin/{nome}")
    public ResponseEntity<UserResponseDTO> findByLogin(@PathVariable String nome) {
        UserResponseDTO user = userService.findByLogin(nome);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
                userService.delete(id);
                        return ResponseEntity.ok().body("usuario deletado com sucesso!");
                } catch (Exception e) {
                        return ResponseEntity.status(500).body("Erro ao deletar usuario");
                }
    }

}
