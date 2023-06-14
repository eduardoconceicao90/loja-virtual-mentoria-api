package com.eduardo.lojavirtual.model.dto.juno;

public class CobrancaJunoAPI {

    private Charge charge = new Charge();
    private Billing billing = new Billing();

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }
}
