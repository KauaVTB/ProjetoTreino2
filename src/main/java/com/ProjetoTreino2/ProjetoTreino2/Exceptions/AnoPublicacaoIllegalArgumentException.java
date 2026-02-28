package com.ProjetoTreino2.ProjetoTreino2.Exceptions;

public class AnoPublicacaoIllegalArgumentException extends RuntimeException{
    public AnoPublicacaoIllegalArgumentException(){
        super("O campo anoPublicacao é obrigatório e não pode ser nulo.");
    }
}
