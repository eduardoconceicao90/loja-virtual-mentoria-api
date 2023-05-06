package com.eduardo.lojavirtual.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "nota_fiscal_compra")
@SequenceGenerator(name = "seq_nota_fiscal_compra", sequenceName = "seq_nota_fiscal_compra", allocationSize = 1, initialValue = 1)
public class NotaFiscalCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_fiscal_compra")
    private Long id;

    @NotNull(message = "Informe o número da nota")
    @Column(nullable = false)
    private String numeroNota;

    @NotEmpty(message = "Informe a série da nota")
    @NotNull(message = "Informe a série da nota")
    @Column(nullable = false)
    private String serieNota;

    private String descricaoObs;

    @NotNull(message = "Informe o total da nota")
    @Column(nullable = false)
    private BigDecimal valorTotal;

    private BigDecimal valorDesconto;

    @NotNull(message = "Informe o valor do ICMS")
    @Column(nullable = false)
    private BigDecimal valorIcms;

    @NotNull(message = "Informe a data da compra")
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCompra;

    @ManyToOne(targetEntity = PessoaJuridica.class)
    @JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
    private PessoaJuridica pessoa;

    @ManyToOne
    @JoinColumn(name = "conta_pagar_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "conta_pagar_fk"))
    private ContaPagar contaPagar;

    @ManyToOne(targetEntity = PessoaJuridica.class)
    @JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
    private PessoaJuridica empresa;

}