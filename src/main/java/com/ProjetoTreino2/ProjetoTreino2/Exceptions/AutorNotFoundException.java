package com.ProjetoTreino2.ProjetoTreino2.Exceptions;

public class AutorNotFoundException extends RuntimeException {
    public AutorNotFoundException() {
        super("Autor não encontrado");
    }
    
}
