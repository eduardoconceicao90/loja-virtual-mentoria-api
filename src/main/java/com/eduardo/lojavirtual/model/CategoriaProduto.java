package com.eduardo.lojavirtual.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "categoria_produto")
@SequenceGenerator(name = "seq_categoria_produto", sequenceName = "seq_categoria_produto", allocationSize = 1, initialValue = 1)
public class CategoriaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categoria_produto")
    private Long id;

    @Column(name = "nome_descricao", nullable = false)
    private String nomeDesc;

}
