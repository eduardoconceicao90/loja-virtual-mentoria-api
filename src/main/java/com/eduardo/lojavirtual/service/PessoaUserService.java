package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.PessoaFisica;
import com.eduardo.lojavirtual.model.PessoaJuridica;
import com.eduardo.lojavirtual.model.Usuario;
import com.eduardo.lojavirtual.model.dto.CepDTO;
import com.eduardo.lojavirtual.model.dto.ConsultaCnpjDTO;
import com.eduardo.lojavirtual.repository.PessoaFisicaRepository;
import com.eduardo.lojavirtual.repository.PessoaJuridicaRepository;
import com.eduardo.lojavirtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;

@Service
public class PessoaUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica juridica) {

        for (int i = 0; i< juridica.getEnderecos().size(); i++) {
            juridica.getEnderecos().get(i).setPessoa(juridica);
            juridica.getEnderecos().get(i).setEmpresa(juridica.getEmpresa());
        }

        juridica = pessoaJuridicaRepository.save(juridica);

        Usuario usuarioPj = usuarioRepository.findUserByPessoa(juridica.getId(), juridica.getEmail());

        if (usuarioPj == null) {

            String constraint = usuarioRepository.consultaConstraintAcesso();
            if (constraint != null) {
                jdbcTemplate.execute("begin; alter table usuario_acesso drop constraint " + constraint +"; commit;");
            }

            usuarioPj = new Usuario();
            usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
            usuarioPj.setEmpresa(juridica);
            usuarioPj.setPessoa(juridica);
            usuarioPj.setLogin(juridica.getEmail());

            String senha = "" + Calendar.getInstance().getTimeInMillis();
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            usuarioPj.setSenha(senhaCript);

            usuarioPj = usuarioRepository.save(usuarioPj);

            usuarioRepository.insereAcessoUser(usuarioPj.getId());
            usuarioRepository.insereAcessoUserPj(usuarioPj.getId(), "ROLE_ADMIN");

            StringBuilder menssagemHtml = new StringBuilder();

            menssagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
            menssagemHtml.append("<b>Login: </b>"+juridica.getEmail()+"<br/>");
            menssagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
            menssagemHtml.append("Obrigado!");

            try {
                serviceSendEmail.enviarEmailHtml("Acesso gerado para Loja Virtual", menssagemHtml.toString() , juridica.getEmail());
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
        return juridica;

    }

    public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {

        for (int i = 0; i< pessoaFisica.getEnderecos().size(); i++) {
            pessoaFisica.getEnderecos().get(i).setPessoa(pessoaFisica);
            pessoaFisica.getEnderecos().get(i).setEmpresa(pessoaFisica.getEmpresa());
        }

        pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);

        Usuario usuarioPf = usuarioRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());

        if (usuarioPf == null) {

            String constraint = usuarioRepository.consultaConstraintAcesso();
            if (constraint != null) {
                jdbcTemplate.execute("begin; alter table usuario_acesso drop constraint " + constraint +"; commit;");
            }

            usuarioPf = new Usuario();
            usuarioPf.setDataAtualSenha(Calendar.getInstance().getTime());
            usuarioPf.setEmpresa(pessoaFisica.getEmpresa());
            usuarioPf.setPessoa(pessoaFisica);
            usuarioPf.setLogin(pessoaFisica.getEmail());

            String senha = "" + Calendar.getInstance().getTimeInMillis();
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            usuarioPf.setSenha(senhaCript);

            usuarioPf = usuarioRepository.save(usuarioPf);

            usuarioRepository.insereAcessoUser(usuarioPf.getId());

            StringBuilder menssagemHtml = new StringBuilder();

            menssagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
            menssagemHtml.append("<b>Login: </b>"+pessoaFisica.getEmail()+"<br/>");
            menssagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
            menssagemHtml.append("Obrigado!");

            try {
                serviceSendEmail.enviarEmailHtml("Acesso gerado para Loja Virtual", menssagemHtml.toString() , pessoaFisica.getEmail());
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
        return pessoaFisica;
    }

    public CepDTO consultaCep(String cep) {
        return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json/", CepDTO.class).getBody();
    }

    public ConsultaCnpjDTO consultaCnpjReceitaWS(String cnpj) {
        return new RestTemplate().getForEntity("https://receitaws.com.br/v1/cnpj/" + cnpj, ConsultaCnpjDTO.class).getBody();
    }

    public Boolean possuiAcesso(String username, String acessos){
        String sql = "select count(1) > 0 from usuario_acesso as ua " +
                     "inner join usuario as u on u.id = ua.usuario_id " +
                     "inner join acesso as a on a.id = ua.acesso_id " +
                     "where u.login = '"+ username + "' " +
                     "and a.descricao in ("+ acessos + ")";

        Query query = entityManager.createNativeQuery(sql);
        return Boolean.valueOf(query.getSingleResult().toString());
    }

}
