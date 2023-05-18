package com.eduardo.lojavirtual.model.dto;

import com.eduardo.lojavirtual.model.CategoriaProduto;
import com.eduardo.lojavirtual.model.MarcaProduto;
import com.eduardo.lojavirtual.model.PessoaJuridica;
import com.eduardo.lojavirtual.model.Produto;

import java.math.BigDecimal;

public class ProdutoDTO {

    private Long id;
    private String tipoUnidade;
    private String nome;
    private Boolean ativo;
    private String descricao;
    private Double peso;
    private Double largura;
    private Double altura;
    private Double profundidade;
    private BigDecimal valorVenda;
    private Integer qtdEstoque;
    private Integer qtdAlertaEstoque;
    private String linkYoutube;
    private Boolean alertaQtdEstoque;
    private Integer qtdeClique = 0;
    private PessoaJuridica empresa;
    private CategoriaProduto categoriaProduto;
    private MarcaProduto marcaProduto;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Produto obj) {
        this.id = obj.getId();
        this.tipoUnidade = obj.getTipoUnidade();
        this.nome = obj.getNome();
        this.ativo = obj.getAtivo();
        this.descricao = obj.getDescricao();
        this.peso = obj.getPeso();
        this.largura = obj.getLargura();
        this.altura = obj.getAltura();
        this.profundidade = obj.getProfundidade();
        this.valorVenda = obj.getValorVenda();
        this.qtdEstoque = obj.getQtdEstoque();
        this.qtdAlertaEstoque = obj.getQtdAlertaEstoque();
        this.linkYoutube = obj.getLinkYoutube();
        this.alertaQtdEstoque = obj.getAlertaQtdEstoque();
        this.qtdeClique = obj.getQtdeClique();
        this.empresa = obj.getEmpresa();
        this.categoriaProduto = obj.getCategoriaProduto();
        this.marcaProduto = obj.getMarcaProduto();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoUnidade() {
        return tipoUnidade;
    }

    public void setTipoUnidade(String tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(Double profundidade) {
        this.profundidade = profundidade;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Integer getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(Integer qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public Integer getQtdAlertaEstoque() {
        return qtdAlertaEstoque;
    }

    public void setQtdAlertaEstoque(Integer qtdAlertaEstoque) {
        this.qtdAlertaEstoque = qtdAlertaEstoque;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public Boolean getAlertaQtdEstoque() {
        return alertaQtdEstoque;
    }

    public void setAlertaQtdEstoque(Boolean alertaQtdEstoque) {
        this.alertaQtdEstoque = alertaQtdEstoque;
    }

    public Integer getQtdeClique() {
        return qtdeClique;
    }

    public void setQtdeClique(Integer qtdeClique) {
        this.qtdeClique = qtdeClique;
    }

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }

    public CategoriaProduto getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public MarcaProduto getMarcaProduto() {
        return marcaProduto;
    }

    public void setMarcaProduto(MarcaProduto marcaProduto) {
        this.marcaProduto = marcaProduto;
    }
}
