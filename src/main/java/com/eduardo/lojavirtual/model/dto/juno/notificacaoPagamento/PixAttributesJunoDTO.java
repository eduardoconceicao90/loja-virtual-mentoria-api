package com.eduardo.lojavirtual.model.dto.juno.notificacaoPagamento;

public class PixAttributesJunoDTO {

    private String txid;
    private String endToEndId;

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getEndToEndId() {
        return endToEndId;
    }

    public void setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
    }
}
