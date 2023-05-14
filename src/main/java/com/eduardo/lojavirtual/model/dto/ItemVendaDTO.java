package com.eduardo.lojavirtual.model.dto;

import com.eduardo.lojavirtual.model.Produto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemVendaDTO {

    private Double quantidade;
    private Produto produto;

}
