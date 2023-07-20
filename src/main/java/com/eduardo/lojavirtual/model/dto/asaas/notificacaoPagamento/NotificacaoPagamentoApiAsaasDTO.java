package com.eduardo.lojavirtual.model.dto.asaas.notificacaoPagamento;

public class NotificacaoPagamentoApiAsaasDTO {

    private String event;
    private PaymentDTO payment = new PaymentDTO();

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }

    public String idFatura() {
        return payment.getId();
    }

    public String statusPagamento() {
        return getPayment().getStatus();
    }

    public Boolean boletoPixFaturaPaga() {
        return statusPagamento().equalsIgnoreCase("CONFIRMED") || statusPagamento().equalsIgnoreCase("RECEIVED");
    }

}
