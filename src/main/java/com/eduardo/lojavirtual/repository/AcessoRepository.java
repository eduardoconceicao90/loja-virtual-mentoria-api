package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.Acesso;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AcessoRepository extends JpaRepository<Acesso, Long> {

    @Query("select a from Acesso a where upper(trim(a.descricao)) like %?1%")
    List<Acesso> buscarAcessoDesc(String desc);

    @Query("select a from Acesso a where a.empresa.id = ?1")
    public List<Acesso> findPorPage(Long idEmpresa, Pageable pageable);

    @Query(value = "SELECT cast((count(1) / 5) as integer) + 1 as qtdpagina FROM acesso WHERE empresa_id = ?1", nativeQuery = true)
    public Integer qdtPagina(Long idEmpresa);

    @Query("select a from Acesso a where upper(trim(a.descricao)) like %?1% and a.empresa.id = ?2")
    List<Acesso> buscarAcessoPorDescEEmpresa(String descricao, Long empresa);

    @Query("select a from Acesso a where a.empresa.id = ?1")
    List<Acesso> listarAcesso(Long idEmpresa);
}
