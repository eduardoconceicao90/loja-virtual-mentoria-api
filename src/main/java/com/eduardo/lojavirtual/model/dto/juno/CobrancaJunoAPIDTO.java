package com.eduardo.lojavirtual.model.dto.juno;

public class CobrancaJunoAPIDTO {

    private ChargeDTO charge = new ChargeDTO();
    private BillingDTO billing = new BillingDTO();

    public ChargeDTO getCharge() {
        return charge;
    }

    public void setCharge(ChargeDTO charge) {
        this.charge = charge;
    }

    public BillingDTO getBilling() {
        return billing;
    }

    public void setBilling(BillingDTO billing) {
        this.billing = billing;
    }
}
