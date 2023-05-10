package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.ImagemProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Long> {

    @Query("select a from ImagemProduto a where a.produto.id = ?1")
    List<ImagemProduto> buscaImagemProduto(Long idProduto);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(value = "delete from imagem_produto where produto_id = ?1", nativeQuery = true)
    void deleteImagem(Long idProduto);

}
