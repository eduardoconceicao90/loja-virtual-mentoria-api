package com.eduardo.lojavirtual.model;

import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id")
public class PessoaJuridica extends Pessoa {

    @CNPJ(message = "CNPJ está inválido")
    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private String insEstadual;

    private String insMunicipal;

    @Column(nullable = false)
    private String nomeFantasia;

    @Column(nullable = false)
    private String razaoSocial;

    private String categoria;

}
