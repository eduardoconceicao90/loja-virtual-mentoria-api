package com.eduardo.lojavirtual.model.dto.webMania;

import java.util.ArrayList;
import java.util.List;

public class NotaFiscalEletronicaDTO {

    private String ID;
    private String url_notificacao;

    // 0 é Entrada e 1 é Saída
    private Integer operacao;

    private String natureza_operacao;

    // 1 é NF-e e 1 é NFC-e
    private String modelo;

    private Integer finalidade;

    // 1 é Produção e 2 é Homologação
    private Integer ambiente;

    private ClienteDTO cliente;
    private List<ProdutoDTO> produtos = new ArrayList<>();
    private PedidoDTO pedido;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUrl_notificacao() {
        return url_notificacao;
    }

    public void setUrl_notificacao(String url_notificacao) {
        this.url_notificacao = url_notificacao;
    }

    public Integer getOperacao() {
        return operacao;
    }

    public void setOperacao(Integer operacao) {
        this.operacao = operacao;
    }

    public String getNatureza_operacao() {
        return natureza_operacao;
    }

    public void setNatureza_operacao(String natureza_operacao) {
        this.natureza_operacao = natureza_operacao;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(Integer finalidade) {
        this.finalidade = finalidade;
    }

    public Integer getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Integer ambiente) {
        this.ambiente = ambiente;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public List<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
    }
}
