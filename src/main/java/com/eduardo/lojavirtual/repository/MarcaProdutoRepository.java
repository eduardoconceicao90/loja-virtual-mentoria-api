package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.MarcaProduto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MarcaProdutoRepository extends JpaRepository<MarcaProduto, Long> {

    @Query("select a from MarcaProduto a where upper(trim(a.nomeDesc)) like %?1%")
    List<MarcaProduto> buscarMarcaDesc(String desc);

    @Query(value = "SELECT cast((count(1) / 5) as integer) + 1 as qtdpagina FROM marca_produto WHERE empresa_id = ?1", nativeQuery = true)
    public Integer qdtPagina(Long idEmpresa);

    @Query("select a from MarcaProduto a where upper(trim(a.nomeDesc)) like %?1% and a.empresa.id = ?2")
    List<MarcaProduto> buscarMarcaPorDescEEmpresa(String nomeDesc, Long empresa);

    @Query("select a from MarcaProduto a where a.empresa.id = ?1")
    public List<MarcaProduto> findPorPage(Long idEmpresa, Pageable pageable);

}
