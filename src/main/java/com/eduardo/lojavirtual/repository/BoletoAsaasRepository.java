package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.BoletoAsaas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletoAsaasRepository extends JpaRepository<BoletoAsaas, Long> {
}
