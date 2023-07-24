package com.eduardo.lojavirtual.model.dto.webMania;

public class ProdutoDTO {

    private String nome;
    private String codigo;
    private String ncm;
    private String cest;
    private Integer quantidade;
    private String unidade;
    private String peso;

    /* 0 - Nacional, exceto as indicadas nos códigos 3, 4, 5 e 8
       1 - Estrangeira - Importação direta, exceto a indicada no código 6
       2 - Estrangeira - Adquirida no mercado interno, exceto a indicada no código 7
       3 - Nacional, mercadoria ou bem com Conteúdo de Importação superior a 40% e inferior ou igual a 70%
       4 - Nacional, cuja produção tenha sido feita em conformidade com os processos produtivos básicos de que tratam as legislações citadas nos Ajustes
       5 - Nacional, mercadoria ou bem com Conteúdo de Importação inferior ou igual a 40%
       6 - Estrangeira - Importação direta, sem similar nacional, constante em lista da CAMEX e gás natural
       7 - Estrangeira - Adquirida no mercado interno, sem similar nacional, constante lista CAMEX e gás natural
       8 - Nacional, mercadoria ou bem com Conteúdo de Importação superior a 70% */
    private Integer origem;

    private String subtotal;
    private String total;
    private String classe_imposto;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getCest() {
        return cest;
    }

    public void setCest(String cest) {
        this.cest = cest;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public Integer getOrigem() {
        return origem;
    }

    public void setOrigem(Integer origem) {
        this.origem = origem;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getClasse_imposto() {
        return classe_imposto;
    }

    public void setClasse_imposto(String classe_imposto) {
        this.classe_imposto = classe_imposto;
    }
}
