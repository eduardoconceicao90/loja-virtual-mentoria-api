package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.CategoriaProduto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {

    @Query(value = "select count(1) > 0 from categoria_produto where upper(trim(nome_descricao)) = upper(trim(?1))", nativeQuery = true)
    public boolean existeCategoria(String nomeCategoria);

    @Query("select a from CategoriaProduto a where upper(trim(a.nomeDesc)) like %?1%")
    public List<CategoriaProduto> buscarCategoriaDes(String nomeDesc);

    @Query("select a from CategoriaProduto a where a.empresa.id = ?1")
    List<CategoriaProduto> findAll(Long codEmpresa);

    @Query("select a from CategoriaProduto a where upper(trim(a.nomeDesc)) like %?1% and a.empresa.id = ?2")
    List<CategoriaProduto> buscarCategoriaPorDescEEmpresa(String nomeDesc, Long empresa);

    @Query(value = "SELECT cast((count(1) / 5) as integer) + 1 as qtdpagina FROM categoria_produto WHERE empresa_id = ?1", nativeQuery = true)
    public Integer qdtPagina(Long idEmpresa);

    @Query("select a from CategoriaProduto a where a.empresa.id = ?1")
    public List<CategoriaProduto> findPorPage(Long idEmpresa, Pageable pageable);
}
