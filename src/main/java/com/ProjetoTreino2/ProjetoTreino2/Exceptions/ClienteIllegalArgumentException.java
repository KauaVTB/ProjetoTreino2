package com.ProjetoTreino2.ProjetoTreino2.Exceptions;

public class ClienteIllegalArgumentException extends RuntimeException {
    public ClienteIllegalArgumentException() {
        super("O campo cliente_id é obrigatório e não pode ser nulo.");
    }
    
}
