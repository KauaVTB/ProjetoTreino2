package com.ProjetoTreino2.ProjetoTreino2.Exceptions;

public class AutorNullException extends RuntimeException {
    public AutorNullException() {
        super("Autor não pode ser nulo");
    }
    
}
