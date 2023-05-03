package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("select a from Produto a where upper(trim(a.nome)) like %?1%")
    public List<Produto> buscarProdutoNome(String nome);

    @Query("select a from Produto a where upper(trim(a.nome)) like %?1% and a.empresa.id = ?2")
    public List<Produto> buscarProdutoNome(String nome, Long idEmpresa);
}