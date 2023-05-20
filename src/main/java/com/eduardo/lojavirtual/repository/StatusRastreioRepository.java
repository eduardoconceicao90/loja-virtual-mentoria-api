package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.StatusRastreio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRastreioRepository extends JpaRepository<StatusRastreio, Long> {

    @Query(value = "select s from StatusRastreio s where s.vendaCompraLojaVirtual.id = ?1 order by s.id")
    public List<StatusRastreio> listaRastreioVenda(Long idVenda);

}
