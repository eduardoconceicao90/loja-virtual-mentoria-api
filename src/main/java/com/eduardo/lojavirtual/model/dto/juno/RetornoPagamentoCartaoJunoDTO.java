package com.eduardo.lojavirtual.model.dto.juno;

import java.util.ArrayList;
import java.util.List;

public class RetornoPagamentoCartaoJunoDTO {

    private String transactionId;
    private String installments;
    private List<PaymentsCartaoCreditoDTO> payments = new ArrayList<PaymentsCartaoCreditoDTO>();
    private List<LinksDTO> _links = new ArrayList<LinksDTO>();

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getInstallments() {
        return installments;
    }

    public void setInstallments(String installments) {
        this.installments = installments;
    }

    public List<PaymentsCartaoCreditoDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentsCartaoCreditoDTO> payments) {
        this.payments = payments;
    }

    public List<LinksDTO> get_links() {
        return _links;
    }

    public void set_links(List<LinksDTO> _links) {
        this._links = _links;
    }
}
