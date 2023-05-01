package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    @Query(value = "select pj from PessoaJuridica pj where upper(trim(pj.nome)) like %?1%")
    public List<PessoaJuridica> pesquisaPorNomePJ(String nome);

    @Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
    public PessoaJuridica existeCnpjCadastrado(String cnpj);

    @Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
    public List<PessoaJuridica> existeCnpjCadastradoList(String cnpj);

    @Query(value = "select pj from PessoaJuridica pj where pj.insEstadual = ?1")
    public PessoaJuridica existeInsEstadualCadastrado(String insEstadual);

    @Query(value = "select pj from PessoaJuridica pj where pj.insEstadual = ?1")
    public List<PessoaJuridica> existeInsEstadualCadastradoList(String insEstadual);

}
