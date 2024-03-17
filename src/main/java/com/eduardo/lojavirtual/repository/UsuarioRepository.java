package com.eduardo.lojavirtual.repository;

import com.eduardo.lojavirtual.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select u from Usuario u where u.login = ?1")
    Usuario findUserByLogin(String login);

    @Query(value = "select u from Usuario u where u.pessoa.id = ?1 or u.login =?2")
    Usuario findUserByPessoa(Long id, String email);

    @Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'usuario_acesso' and column_name = 'acesso_id' and constraint_name <> 'unique_acesso_user';", nativeQuery = true)
    String consultaConstraintAcesso();

    @Transactional
    @Modifying
    @Query(value = "insert into usuario_acesso(usuario_id, acesso_id) values (?1, (select id from acesso where descricao = ?2 limit 1))", nativeQuery = true)
    void insereAcessoUserPj(Long iduser, String acesso);

    @Transactional
    @Modifying
    @Query(value = "insert into usuario_acesso(usuario_id, acesso_id) values (?1, (select id from acesso where descricao = 'ROLE_USER'))", nativeQuery = true)
    void insereAcessoUser(Long iduser);

    @Query(value = "select u from Usuario u where u.dataAtualSenha <= current_date - 90")
    List<Usuario> usuarioSenhaVencida();
    
    @Transactional
    @Modifying
    @Query(value = "update usuario set senha = ?1 where login =?2", nativeQuery = true)
    void updateSenhaUser(String senha, String login);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(value = "delete from usuario where empresa_id = ?1", nativeQuery = true)
    void deleteByPj(Long id);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(value = "delete from usuario_acesso where usuario_id in (select distinct usuario_id from usuario_acesso where usuario_id in (select id from usuario where empresa_id = ?1))", nativeQuery = true)
    void deleteAcessoUser(Long id);
}
