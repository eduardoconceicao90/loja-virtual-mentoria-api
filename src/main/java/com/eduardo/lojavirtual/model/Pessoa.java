package com.eduardo.lojavirtual.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name = "seq_pessoa",sequenceName = "seq_pessoa", initialValue = 1, allocationSize = 1)
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pessoa")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column
    private String tipoPessoa;

    @OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Endereco> enderecos = new ArrayList<>();

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "empresa_id", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
    private Pessoa empresa;

}
