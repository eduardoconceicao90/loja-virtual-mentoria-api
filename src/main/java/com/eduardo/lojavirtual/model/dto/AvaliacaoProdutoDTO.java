package com.eduardo.lojavirtual.model.dto;

import com.eduardo.lojavirtual.model.AvaliacaoProduto;
import com.eduardo.lojavirtual.model.PessoaFisica;

public class AvaliacaoProdutoDTO {

    private Long id;
    private String descricao;
    private Integer nota;
    private PessoaFisica pessoa;

    public AvaliacaoProdutoDTO(AvaliacaoProduto obj) {
        this.id = obj.getId();
        this.descricao = obj.getDescricao();
        this.nota = obj.getNota();
        this.pessoa = obj.getPessoa();
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

    public PessoaFisica getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaFisica pessoa) {
        this.pessoa = pessoa;
    }
}
