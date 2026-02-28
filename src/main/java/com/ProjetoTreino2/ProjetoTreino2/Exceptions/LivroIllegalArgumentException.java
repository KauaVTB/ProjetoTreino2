package com.ProjetoTreino2.ProjetoTreino2.Exceptions;

public class LivroIllegalArgumentException extends RuntimeException {
    public LivroIllegalArgumentException() {
        super("O campo livro_id é obrigatório e não pode ser nulo.");
    }
    
}
