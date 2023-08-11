package com.eduardo.lojavirtual.model.dto;

import com.eduardo.lojavirtual.model.AvaliacaoProduto;

public class AvaliacaoProdutoDTO {

    private Long id;
    private String descricao;
    private Integer nota;
    private ProdutoDTO produtoDTO;

    public AvaliacaoProdutoDTO(AvaliacaoProduto obj) {
        this.id = obj.getId();
        this.descricao = obj.getDescricao();
        this.nota = obj.getNota();
        this.produtoDTO = new ProdutoDTO(obj.getProduto());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public ProdutoDTO getProdutoDTO() {
        return produtoDTO;
    }

    public void setProdutoDTO(ProdutoDTO produtoDTO) {
        this.produtoDTO = produtoDTO;
    }
}
