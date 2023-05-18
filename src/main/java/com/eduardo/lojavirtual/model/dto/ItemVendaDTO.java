package com.eduardo.lojavirtual.model.dto;

import com.eduardo.lojavirtual.model.Produto;

public class ItemVendaDTO {

    private Double quantidade;
    private ProdutoDTO produtoDTO;

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public ProdutoDTO getProdutoDTO() {
        return produtoDTO;
    }

    public void setProdutoDTO(ProdutoDTO produtoDTO) {
        this.produtoDTO = produtoDTO;
    }
}
