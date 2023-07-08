package com.eduardo.lojavirtual.model.dto.juno;

public class BillingCartaoCreditoDTO {

    private String email = "";
    private boolean delayed = false;
    private AddressCartaoCreditoDTO address = new AddressCartaoCreditoDTO();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    public AddressCartaoCreditoDTO getAddress() {
        return address;
    }

    public void setAddress(AddressCartaoCreditoDTO address) {
        this.address = address;
    }
}
