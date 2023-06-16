package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.BoletoJuno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletoJunoRepository extends JpaRepository<BoletoJuno, Long> {

}
