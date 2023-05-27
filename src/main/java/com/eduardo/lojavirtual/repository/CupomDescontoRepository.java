package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.CupomDesconto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CupomDescontoRepository extends JpaRepository<CupomDesconto, Long> {

    @Query(value = "select c from CupomDesconto c where c.empresa.id = ?1")
    List<CupomDesconto> cupomDescontoPorEmpresa(Long idEmpresa);

}
