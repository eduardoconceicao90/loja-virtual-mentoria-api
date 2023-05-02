package com.eduardo.lojavirtual.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoPessoa {

    JURIDICA("Jurídica"),
    JURIDICA_FORNECEDOR("Jurídica e Fornecedor"),
    FISICA("Física");

    private String descricao;

    @Override
    public String toString() {
        return this.descricao;
    }
}
