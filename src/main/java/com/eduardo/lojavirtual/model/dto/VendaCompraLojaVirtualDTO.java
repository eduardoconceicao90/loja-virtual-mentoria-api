package com.eduardo.lojavirtual.model.dto;

import com.eduardo.lojavirtual.model.Endereco;
import com.eduardo.lojavirtual.model.Pessoa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendaCompraLojaVirtualDTO {

    private Long id;
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private BigDecimal valorDesc = BigDecimal.ZERO;
    private Pessoa pessoa;
    private Endereco cobranca;
    private Endereco entrega;
    private BigDecimal valorFrete = BigDecimal.ZERO;
    private List<ItemVendaDTO> itemVendaLoja = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDesc() {

        if (valorDesc == null) {
            return BigDecimal.ZERO;
        }

        return valorDesc;
    }

    public void setValorDesc(BigDecimal valorDesc) {
        this.valorDesc = valorDesc;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getCobranca() {
        return cobranca;
    }

    public void setCobranca(Endereco cobranca) {
        this.cobranca = cobranca;
    }

    public Endereco getEntrega() {
        return entrega;
    }

    public void setEntrega(Endereco entrega) {
        this.entrega = entrega;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public List<ItemVendaDTO> getItemVendaLoja() {
        return itemVendaLoja;
    }

    public void setItemVendaLoja(List<ItemVendaDTO> itemVendaLoja) {
        this.itemVendaLoja = itemVendaLoja;
    }
}