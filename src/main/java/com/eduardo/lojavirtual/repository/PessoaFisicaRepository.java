package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.PessoaFisica;
import com.eduardo.lojavirtual.model.PessoaJuridica;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

    @Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
    public PessoaFisica existeCpfCadastrado(String cpf);

    @Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
    public List<PessoaFisica> existeCpfCadastradoList(String cpf);

    @Query(value = "select pf from PessoaFisica pf where upper(trim(pf.nome)) like %?1%")
    public List<PessoaFisica> pesquisaPorNomePF(String nome);

    @Query("select a from PessoaFisica a where a.empresa.id = ?1")
    public List<PessoaFisica> findPorPage(Long idEmpresa, Pageable pageable);

    @Query(value = "SELECT cast((count(1) / 5) as integer) + 1 as qtdpagina FROM pessoa_fisica WHERE empresa_id = ?1", nativeQuery = true)
    public Integer qdtPagina(Long idEmpresa);

    @Query("select a from PessoaFisica a where upper(trim(a.nome)) like %?1% and a.empresa.id = ?2")
    List<PessoaFisica> buscarPfPorNomeEEmpresa(String nome, Long empresa);

}
