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

    @Query(value = "select u from Usuario u where u.empresa.id = ?1")
    List<Usuario> listUserByEmpresa(Long idEmpresa);

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
    @Modifying
    @Query(value = "update usuario set senha = ?1 where id = ?2", nativeQuery = true)
    void updateSenhaUserId(String senha, Long id);

    @Query(value = "select count(1) > 0 from usuario where senha = ?1 and id = ?2", nativeQuery = true)
    Boolean senhaIgual(String login, Long id);

    @Transactional
    @Modifying
    @Query(value = "update usuario set login = ?1 where id =?2", nativeQuery = true)
    void updateLoginUser(String login, Long id);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(value = "delete from usuario where pessoa_id = ?1", nativeQuery = true)
    void deleteByPessoa(Long id);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(value = "delete from usuario_acesso where usuario_id in (select distinct usuario_id from usuario_acesso where usuario_id in (select id from usuario where pessoa_id = ?1))", nativeQuery = true)
    void deleteAcessoUser(Long id);

    @Query(value = "select count(1) > 0 from usuario_acesso where acesso_id = ?1 and usuario_id = ?2", nativeQuery = true)
    Boolean possuiAcesso(Long idAcesso, Long idUser);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(value = "delete from usuario_acesso where acesso_id = ?1 and usuario_id ?2", nativeQuery = true)
    void deleteByAcesso(Long idAcesso, Long idUser);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(value = "insert into usuario_acesso (acesso_id, usuario_id) values (?1, ?2)", nativeQuery = true)
    void adicionarAcesso(Long idAcesso, Long idUser);
}
