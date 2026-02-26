package com.ProjetoTreino2.ProjetoTreino2.Repository;

import com.ProjetoTreino2.ProjetoTreino2.Entities.Cliente;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends org.springframework.data.jpa.repository.JpaRepository<Cliente, Long> {
    Optional<Cliente> findById(Long id);

    List<Cliente> findAll();
}
