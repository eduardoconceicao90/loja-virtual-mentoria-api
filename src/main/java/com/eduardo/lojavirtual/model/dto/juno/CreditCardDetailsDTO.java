package com.eduardo.lojavirtual.model.dto.juno;

public class CreditCardDetailsDTO {

    private String creditCardId = "";
    private String creditCardHash = "";

    public String getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCreditCardHash() {
        return creditCardHash;
    }

    public void setCreditCardHash(String creditCardHash) {
        this.creditCardHash = creditCardHash;
    }
}
