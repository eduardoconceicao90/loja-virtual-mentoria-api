package com.eduardo.lojavirtual.model.dto.webMania;

public class PedidoDTO {

    // 0 é a vista e 1 é a prazo
    private Integer pagamento;

    /* 0 - Não se aplica (por exemplo, Nota Fiscal complementar ou de ajuste)
       1 - Operação presencial
       2 - Operação não presencial, pela Internet
       3 - Operação não presencial, Teleatendimento
       4 - NFC-e em operação com entrega a domicílio
       5 - Operação presencial, fora do estabelecimento
       9 - Operação não presencial, outros */
    private Integer presenca;

    /* 0 - Contratação do Frete por conta do Remetente (CIF)
       1 - Contratação do Frete por conta do Destinatário (FOB)
       2 - Contratação do Frete por conta de Terceiros
       3 - Transporte Próprio por conta do Remetente
       4 - Transporte Próprio por conta do Destinatário
       9 - Sem Ocorrência de Transporte */
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
