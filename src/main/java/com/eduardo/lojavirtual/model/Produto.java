package com.eduardo.lojavirtual.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "produto")
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", allocationSize = 1, initialValue = 1)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
    private Long id;

    private String tipoUnidade;
    private String nome;
    private Boolean ativo = Boolean.TRUE;

    @Column(columnDefinition = "text", length = 2000)
    private String descricao;

    /* Nota item nota produto - ASSOCIAR */

    private Double peso;
    private Double largura;
    private Double altura;
    private Double profundidade;
    private BigDecimal valorVenda = BigDecimal.ZERO;
    private Integer qtdEstoque = 0;
    private Integer qtdAlertaEstoque = 0;
    private String linkYoutube;
    private Boolean alertaQtdEstoque = Boolean.FALSE;
    private Integer qtdClique = 0;

}
