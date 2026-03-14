package com.ProjetoTreino2.ProjetoTreino2.Services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ProjetoTreino2.ProjetoTreino2.Entities.User;
import com.ProjetoTreino2.ProjetoTreino2.Exceptions.LivroNotFoundException;
import com.ProjetoTreino2.ProjetoTreino2.Exceptions.UserNotFoundException;
import com.ProjetoTreino2.ProjetoTreino2.Repository.UserRepository;
import com.ProjetoTreino2.ProjetoTreino2.dto.UserResponseDTO;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> findAll(){
        return userRepository.findAll().stream().map(user -> new UserResponseDTO(
            user.getId(),
            user.getLogin(),
            user.getRole()
        )).toList();
    }

    public UserResponseDTO findByLogin(String nome){
        UserDetails userDetails = userRepository.findByLogin(nome);
        if (userDetails == null) {
        throw new UserNotFoundException(); 
    }
    User user = (User) userDetails;
        return new UserResponseDTO(user.getId(), user.getLogin(), user.getRole());
    }

    public void delete(Long Id){
        userRepository.findById(Id);
        userRepository.deleteById(Id);
    }
}
