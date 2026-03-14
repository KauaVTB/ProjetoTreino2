package com.ProjetoTreino2.ProjetoTreino2.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuario não encontrado");
    }
    
}
