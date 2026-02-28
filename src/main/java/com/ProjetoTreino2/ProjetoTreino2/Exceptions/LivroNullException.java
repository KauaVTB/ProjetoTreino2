package com.ProjetoTreino2.ProjetoTreino2.Exceptions;

public class LivroNullException extends RuntimeException {
    public LivroNullException() {
        super("Livro não pode ser nulo");
    }
    
}
