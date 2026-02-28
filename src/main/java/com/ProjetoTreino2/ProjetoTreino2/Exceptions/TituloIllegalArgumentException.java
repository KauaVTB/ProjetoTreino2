package com.ProjetoTreino2.ProjetoTreino2.Exceptions;

public class TituloIllegalArgumentException extends RuntimeException {
    public TituloIllegalArgumentException() {
        super("O campo titulo é obrigatório e não pode ser nulo.");
    }
    
}
