package com.eduardo.lojavirtual.model.dto.webMania;

public class PedidoDTO {

    private Integer pagamento;
    private Integer presenca;
    private Integer modalidade_frete;
    private String frete;
    private String desconto;
    private String total;

    public Integer getPagamento() {
        return pagamento;
    }

    public void setPagamento(Integer pagamento) {
        this.pagamento = pagamento;
    }

    public Integer getPresenca() {
        return presenca;
    }

    public void setPresenca(Integer presenca) {
        this.presenca = presenca;
    }

    public Integer getModalidade_frete() {
        return modalidade_frete;
    }

    public void setModalidade_frete(Integer modalidade_frete) {
        this.modalidade_frete = modalidade_frete;
    }

    public String getFrete() {
        return frete;
    }

    public void setFrete(String frete) {
        this.frete = frete;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
