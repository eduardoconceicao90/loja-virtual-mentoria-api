package com.eduardo.lojavirtual.model.dto;

import com.eduardo.lojavirtual.model.AvaliacaoProduto;
import com.eduardo.lojavirtual.model.PessoaFisica;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
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
}
