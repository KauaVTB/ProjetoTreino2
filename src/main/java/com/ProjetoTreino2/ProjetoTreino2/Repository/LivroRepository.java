package com.ProjetoTreino2.ProjetoTreino2.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ProjetoTreino2.ProjetoTreino2.Entities.Livro;

@Repository
public interface LivroRepository
        extends
        org.springframework.data.jpa.repository.JpaRepository<com.ProjetoTreino2.ProjetoTreino2.Entities.Livro, Long> {

    Livro findByTitulo(String titulo);

    Livro findByAutor(long id);

    List<Livro> findByAnoPublicacao(int anoPublicacao);

    @Query("SELECT l FROM Livro l WHERE l.anoPublicacao > :anoPublicacao")
    List<Livro> findByAnoPublicacaoMaiorQue(Integer anoPublicacao);

    @Query("SELECT l FROM Livro l WHERE l.anoPublicacao < :anoPublicacao")
    List<Livro> findByAnoPublicacaoMenorQue(Integer anoPublicacao);
}
