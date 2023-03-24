package com.eduardo.lojavirtual.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id")
public class PessoaJuridica extends Pessoa {

    private String cnpj;
    private String insEstatual;
    private String insMunicipal;
    private String nomeFantasia;
    private String razaoSocial;
    private String categoria;

}
