package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.BoletoJuno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BoletoJunoRepository extends JpaRepository<BoletoJuno, Long> {

    @Query("select b from BoletoJuno b where b.code = ?1")
    public BoletoJuno findByCode (String code);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true, value = "update boleto_juno set quitado = true where code = ?1")
    public void quitarBoleto(String code);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true, value = "update boleto_juno set quitado = true where id = ?1")
    public void quitarBoletoById(Long id);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true, value = "delete from boleto_juno where code = ?1")
    public void deleteByCode(String code);
}
