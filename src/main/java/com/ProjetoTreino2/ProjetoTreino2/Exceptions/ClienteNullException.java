package com.ProjetoTreino2.ProjetoTreino2.Exceptions;

public class ClienteNullException extends RuntimeException {
    public ClienteNullException() {
        super("Cliente não pode ser nulo");
    }
    
}
