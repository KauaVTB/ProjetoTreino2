package com.ProjetoTreino2.ProjetoTreino2.Repository;

import org.springframework.stereotype.Repository;



@Repository
public interface AutorRepository extends
        org.springframework.data.jpa.repository.JpaRepository<com.ProjetoTreino2.ProjetoTreino2.Entities.Autor, Long> {

}
