package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaJuridica, Long> {
}
