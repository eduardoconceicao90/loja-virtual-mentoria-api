package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.AccessTokenJunoAPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AccessTokenJunoRepository extends JpaRepository<AccessTokenJunoAPI, Long> {

}
