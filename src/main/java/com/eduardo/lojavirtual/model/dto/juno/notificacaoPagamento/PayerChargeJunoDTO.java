package com.eduardo.lojavirtual.model.dto.juno.notificacaoPagamento;

import com.eduardo.lojavirtual.model.dto.juno.notificacaoPagamento.AddressPayerJunoDTO;

public class PayerChargeJunoDTO {

    private String name;
    private String document;
    private String id;

    private AddressPayerJunoDTO address = new AddressPayerJunoDTO();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AddressPayerJunoDTO getAddress() {
        return address;
    }

    public void setAddress(AddressPayerJunoDTO address) {
        this.address = address;
    }
}
