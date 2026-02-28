package com.ProjetoTreino2.ProjetoTreino2.Exceptions;

public class LivroNotFoundException extends RuntimeException {
    public LivroNotFoundException() {
        super("Livro não encontrado");
    }
     
}
