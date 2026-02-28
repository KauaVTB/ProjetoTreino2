package com.ProjetoTreino2.ProjetoTreino2.Exceptions;

public class ClienteNotFoundException  extends RuntimeException {
    public ClienteNotFoundException() {
        super("Cliente não encontrado");
    }
    
}
