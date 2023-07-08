package com.eduardo.lojavirtual.model.dto.juno;

public class PagamentoCartaoCreditoDTO {

    private String chargeId = "";
    private BillingCartaoCreditoDTO billing = new BillingCartaoCreditoDTO();
    private CreditCardDetailsDTO creditCardDetails = new CreditCardDetailsDTO();

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public BillingCartaoCreditoDTO getBilling() {
        return billing;
    }

    public void setBilling(BillingCartaoCreditoDTO billing) {
        this.billing = billing;
    }

    public CreditCardDetailsDTO getCreditCardDetails() {
        return creditCardDetails;
    }

    public void setCreditCardDetails(CreditCardDetailsDTO creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
    }
}
