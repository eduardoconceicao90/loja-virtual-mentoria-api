package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.PessoaFisica;
import com.eduardo.lojavirtual.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaJuridica, Long> {

    @Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
    public PessoaJuridica existeCnpjCadastrado(String cnpj);

    @Query(value = "select pj from PessoaJuridica pj where pj.insEstadual = ?1")
    public PessoaJuridica existeInsEstadualCadastrado(String insEstadual);
}
