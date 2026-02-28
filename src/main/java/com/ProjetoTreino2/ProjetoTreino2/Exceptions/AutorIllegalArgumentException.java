package com.ProjetoTreino2.ProjetoTreino2.Exceptions;

public class AutorIllegalArgumentException extends RuntimeException {
    public AutorIllegalArgumentException() {
        super("O campo autor_id é obrigatório e não pode ser nulo.");
    }
    
}
