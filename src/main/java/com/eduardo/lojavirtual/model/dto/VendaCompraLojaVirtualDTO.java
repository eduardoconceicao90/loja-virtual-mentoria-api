package com.eduardo.lojavirtual.model.dto;

import com.eduardo.lojavirtual.model.Endereco;
import com.eduardo.lojavirtual.model.Pessoa;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class VendaCompraLojaVirtualDTO {

    private Long id;
    private BigDecimal valorTotal;
    private BigDecimal valorDesc;
    private Pessoa pessoa;
    private Endereco cobranca;
    private Endereco entrega;
    private BigDecimal valorFrete;
    private List<ItemVendaDTO> itemVendaLoja = new ArrayList<>();
}