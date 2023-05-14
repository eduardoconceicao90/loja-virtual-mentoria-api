package com.eduardo.lojavirtual.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ConsultaCnpjDTO {

    private List<AtividadeDTO> atividade_principal = new ArrayList<AtividadeDTO>();

    private String data_situacao;
    private String tipo;
    private String nome;
    private String uf;
    private String telefone;
    private String email;

    private List<AtividadeDTO> atividades_secundarias = new ArrayList<AtividadeDTO>();

    private List<QsaDTO> qsa = new ArrayList<QsaDTO>();

    private String situacao;
    private String bairro;
    private String logradouro;
    private String numero;
    private String cep;
    private String municipio;
    private String porte;
    private String abertura;
    private String natureza_juridica;
    private String fantasia;
    private String cnpj;
    private String ultima_atualizacao;
    private String status;
    private String complemento;
    private String efr;
    private String motivo_situacao;
    private String situacao_especial;
    private String data_situacao_especial;
    private String capital_social;

    @JsonIgnore
    private ExtraDTO extra;

    private BillingDTO billing;

}
