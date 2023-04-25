package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

    @Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
    public PessoaFisica existeCpfCadastrado(String cpf);
}
