package com.eduardo.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eduardo.lojavirtual.model.BoletoAsaas;

@Repository
public interface BoletoAsaasRepository extends JpaRepository<BoletoAsaas, Long> {

    @Query("select b from BoletoAsaas b where b.vendaCompraLojaVirtual.id = ?1 and b.quitado = false")
    public List<BoletoAsaas> cobrancaDaVendaCompra(Long idVendaCompra);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true, value = "update boleto_asaas set quitado = true where id = ?1")
    public void quitarBoletoById(Long id);

    @Query("select b from BoletoAsaas b where b.code = ?1")
    public BoletoAsaas findByCode (String code);

}
