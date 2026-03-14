package com.ProjetoTreino2.ProjetoTreino2.Repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ProjetoTreino2.ProjetoTreino2.Entities.Autor;



@Repository
public interface AutorRepository extends
                org.springframework.data.jpa.repository.JpaRepository<com.ProjetoTreino2.ProjetoTreino2.Entities.Autor, Long> {
                    Optional<Autor> findByNome(String nome);
}
