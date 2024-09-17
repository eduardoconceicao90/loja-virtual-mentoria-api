package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.*;
import com.eduardo.lojavirtual.model.dto.CepDTO;
import com.eduardo.lojavirtual.model.dto.ConsultaCnpjDTO;
import com.eduardo.lojavirtual.model.dto.ObjetoMsgGeralDTO;
import com.eduardo.lojavirtual.model.enums.TipoPessoa;
import com.eduardo.lojavirtual.repository.EnderecoRepository;
import com.eduardo.lojavirtual.repository.PessoaFisicaRepository;
import com.eduardo.lojavirtual.repository.PessoaJuridicaRepository;
import com.eduardo.lojavirtual.repository.UsuarioRepository;
import com.eduardo.lojavirtual.service.ContagemAcessoApi;
import com.eduardo.lojavirtual.service.PessoaUserService;
import com.eduardo.lojavirtual.service.ServiceSendEmail;
import com.eduardo.lojavirtual.util.ValidaCNPJ;
import com.eduardo.lojavirtual.util.ValidaCPF;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class PessoaController {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaUserService pessoaUserService;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ContagemAcessoApi contagemAcessoApi;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ServiceSendEmail sendEmail;

    @ResponseBody
    @GetMapping(value = "**/consultaCep/{cep}")
    public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep){
        return new ResponseEntity<CepDTO>(pessoaUserService.consultaCep(cep), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaCnpjReceitaWs/{cnpj}")
    public ResponseEntity<ConsultaCnpjDTO> consultaCnpjReceitaWs(@PathVariable("cnpj") String cnpj){
        return new ResponseEntity<ConsultaCnpjDTO>(pessoaUserService.consultaCnpjReceitaWS(cnpj), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaPfNome/{nome}")
    public ResponseEntity<List<PessoaFisica>> consultaPfNome(@PathVariable("nome") String nome) {
        List<PessoaFisica> fisicas = pessoaFisicaRepository.pesquisaPorNomePF(nome.trim().toUpperCase());

        // Opcional caso deseje gravar no bd a quantidade de vezes que o end-point foi executado.
        contagemAcessoApi.atualizaAcessoEndPointPF();

        return new ResponseEntity<List<PessoaFisica>>(fisicas, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaPfCpf/{cpf}")
    public ResponseEntity<List<PessoaFisica>> consultaPfCpf(@PathVariable("cpf") String cpf) {
        List<PessoaFisica> fisicas = pessoaFisicaRepository.existeCpfCadastradoList(cpf);
        return new ResponseEntity<List<PessoaFisica>>(fisicas, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaNomePJ/{nome}")
    public ResponseEntity<List<PessoaJuridica>> consultaNomePJ(@PathVariable("nome") String nome) {
        List<PessoaJuridica> fisicas = pessoaJuridicaRepository.pesquisaPorNomePJ(nome.trim().toUpperCase());
        return new ResponseEntity<List<PessoaJuridica>>(fisicas, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaCnpjPJ/{cnpj}")
    public ResponseEntity<List<PessoaJuridica>> consultaCnpjPJ(@PathVariable("cnpj") String cnpj) {
        List<PessoaJuridica> fisicas = pessoaJuridicaRepository.existeCnpjCadastradoList(cnpj.trim().toUpperCase());
        return new ResponseEntity<List<PessoaJuridica>>(fisicas, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/salvarPj")
    public ResponseEntity<PessoaJuridica> salvarPj(@Valid @RequestBody PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava{

        if (pessoaJuridica == null) {
            throw new ExceptionMentoriaJava("Pessoa juridica não pode ser NULL");
        }

        if (pessoaJuridica.getTipoPessoa() == null) {
            throw new ExceptionMentoriaJava("Informe o tipo Jurídico ou Fornecedor da Loja");
        }

        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
            throw new ExceptionMentoriaJava("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
        }

        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeInsEstadualCadastrado(pessoaJuridica.getInsEstadual()) != null) {
            throw new ExceptionMentoriaJava("Já existe Inscrição estadual cadastrado com o número: " + pessoaJuridica.getInsEstadual());
        }

        if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
            throw new ExceptionMentoriaJava("Cnpj: " + pessoaJuridica.getCnpj() + " está inválido.");
        }

        if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {

            for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {

                String cep = pessoaJuridica.getEnderecos().get(p).getCep();
                CepDTO cepDTO = pessoaUserService.consultaCep(cep);

                if(cepDTO == null || (cepDTO != null && cepDTO.getCep() == null)){
                    throw new ExceptionMentoriaJava("CEP: " + cep + " está inválido.");
                }

                pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
                pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
                pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
                pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDTO.getLogradouro());
                pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
                pessoaJuridica.getEnderecos().get(p).setEmpresa(pessoaJuridica.getEmpresa());
                pessoaJuridica.getEnderecos().get(p).setPessoa(pessoaJuridica);

            }
        } else {

            for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {

                Long idCep = pessoaJuridica.getEnderecos().get(p).getId();

                if(idCep != null){
                    Endereco enderecoTemp =  enderecoRepository.findById(idCep).get();

                    if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {

                        String cep = pessoaJuridica.getEnderecos().get(p).getCep();
                        CepDTO cepDTO = pessoaUserService.consultaCep(cep);

                        if(cepDTO == null || (cepDTO != null && cepDTO.getCep() == null)){
                            throw new ExceptionMentoriaJava("CEP: " + cep + " está inválido.");
                        }

                        pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
                        pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
                        pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
                        pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDTO.getLogradouro());
                        pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
                        pessoaJuridica.getEnderecos().get(p).setEmpresa(pessoaJuridica.getEmpresa());
                        pessoaJuridica.getEnderecos().get(p).setPessoa(pessoaJuridica);
                    }
                }

            }
        }

        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);

        return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/salvarPf")
    public ResponseEntity<PessoaFisica> salvarPf(@Valid @RequestBody PessoaFisica pessoaFisica) throws ExceptionMentoriaJava{

        if (pessoaFisica == null) {
            throw new ExceptionMentoriaJava("Pessoa fisica não pode ser NULL");
        }

        if (pessoaFisica.getTipoPessoa() == null) {
            pessoaFisica.setTipoPessoa(TipoPessoa.FISICA.name());
        }

        if (pessoaFisica.getId() == null && pessoaFisicaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null) {
            throw new ExceptionMentoriaJava("Já existe CPF cadastrado com o número: " + pessoaFisica.getCpf());
        }

        if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
            throw new ExceptionMentoriaJava("CPF : " + pessoaFisica.getCpf() + " está inválido.");
        }

        pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);

        return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
    }
    
    @ResponseBody
    @PostMapping(value = "**/recuperarSenha")
    public ResponseEntity<ObjetoMsgGeralDTO> recuperarAcesso(@RequestBody String login) throws Exception {
    	Usuario usuario = usuarioRepository.findUserByLogin(login);
    	
    	if(usuario == null) {
    		return new ResponseEntity<ObjetoMsgGeralDTO>(new ObjetoMsgGeralDTO("Usuário não encontrado"), HttpStatus.OK);
    	}
    	
    	String senha = UUID.randomUUID().toString();
    	senha = senha.substring(0, 6);
    	
    	String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
    	
    	usuarioRepository.updateSenhaUser(senhaCriptografada, login);
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("<b>Sua nova senha é: </b>").append(senha);
    	sendEmail.enviarEmailHtml("Sua nova senha", sb.toString(), usuario.getLogin());
    		
    	return new ResponseEntity<ObjetoMsgGeralDTO>(new ObjetoMsgGeralDTO("Senha enviada para seu e-mail"), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/deletarPj")
    public ResponseEntity<String> delete(@RequestBody PessoaJuridica pj){
        usuarioRepository.deleteAcessoUser(pj.getId());
        usuarioRepository.deleteByPessoa(pj.getId());
        pessoaJuridicaRepository.deleteById(pj.getId());
        return new ResponseEntity<String>(new Gson().toJson("Pessoa Juridica removida"), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPjPorId/{id}")
    public ResponseEntity<PessoaJuridica> buscarPjPorId(@PathVariable Long id) throws ExceptionMentoriaJava {
        PessoaJuridica pj = pessoaJuridicaRepository.findById(id).orElse(null);
        if (pj == null){
            throw new ExceptionMentoriaJava("Não encontrou Pessoa Juridica com o codigo: " + id);
        }
        return new ResponseEntity(pj, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/possuiAcesso/{username}/{role}")
    public ResponseEntity<Boolean> possuiAcesso(@PathVariable String username, @PathVariable String role){
        String sqlRole = "'" + role.replace(",", "','") + "'";
        Boolean possuiAcesso = pessoaUserService.possuiAcesso(username, sqlRole);
        return new ResponseEntity<Boolean>(possuiAcesso, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPjPorNomeFantasiaEEmpresa/{nomeFantasia}/{empresa}")
    public ResponseEntity<List<PessoaJuridica>> buscarPjPorNomeFantasiaEEmpresa(@PathVariable("nomeFantasia") String nomeFantasia, @PathVariable("empresa") Long empresa) {
        List<PessoaJuridica> pj = pessoaJuridicaRepository.buscarPjPorNomeFantasiaEEmpresa(nomeFantasia.toUpperCase(), empresa);
        return new ResponseEntity<List<PessoaJuridica>>(pj, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/qtdPaginaPj/{idEmpresa}")
    public ResponseEntity<Integer> qtdPaginaPj(@PathVariable Long idEmpresa){
        Integer qtdPagina = pessoaJuridicaRepository.qdtPagina(idEmpresa);
        return new ResponseEntity<Integer>(qtdPagina, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listaPorPagePj/{idEmpresa}/{pagina}")
    public ResponseEntity<List<PessoaJuridica>> pagesPj(@PathVariable Long idEmpresa,
                                              @PathVariable Integer pagina){
        Pageable pages = PageRequest.of(pagina, 5, Sort.by("nomeFantasia"));
        List<PessoaJuridica> lista = pessoaJuridicaRepository.findPorPage(idEmpresa, pages);
        return new ResponseEntity<List<PessoaJuridica>>(lista, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPfPorNomeEEmpresa/{nome}/{empresa}")
    public ResponseEntity<List<PessoaFisica>> buscarPfPorNomeEEmpresa(@PathVariable("nome") String nome, @PathVariable("empresa") Long empresa) {
        List<PessoaFisica> pf = pessoaFisicaRepository.buscarPfPorNomeEEmpresa(nome.toUpperCase(), empresa);
        return new ResponseEntity<List<PessoaFisica>>(pf, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/qtdPaginaPf/{idEmpresa}")
    public ResponseEntity<Integer> qtdPaginaPf(@PathVariable Long idEmpresa){
        Integer qtdPagina = pessoaFisicaRepository.qdtPagina(idEmpresa);
        return new ResponseEntity<Integer>(qtdPagina, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listaPorPagePf/{idEmpresa}/{pagina}")
    public ResponseEntity<List<PessoaFisica>> pagesPf(@PathVariable Long idEmpresa,
                                                        @PathVariable Integer pagina){
        Pageable pages = PageRequest.of(pagina, 5, Sort.by("nome"));
        List<PessoaFisica> lista = pessoaFisicaRepository.findPorPage(idEmpresa, pages);
        return new ResponseEntity<List<PessoaFisica>>(lista, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/deletarPf")
    public ResponseEntity<String> delete(@RequestBody PessoaFisica pf){
        usuarioRepository.deleteAcessoUser(pf.getId());
        usuarioRepository.deleteByPessoa(pf.getId());
        pessoaFisicaRepository.deleteById(pf.getId());
        return new ResponseEntity<String>(new Gson().toJson("Pessoa Física removida"), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPfPorId/{id}")
    public ResponseEntity<PessoaFisica> buscarPfPorId(@PathVariable Long id) throws ExceptionMentoriaJava {
        PessoaFisica pf = pessoaFisicaRepository.findById(id).orElse(null);
        if (pf == null){
            throw new ExceptionMentoriaJava("Não encontrou Pessoa Física com o codigo: " + id);
        }
        return new ResponseEntity(pf, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listUserByEmpresa/{idEmpresa}")
    public ResponseEntity<List<Usuario>> listUserByEmpresa(@PathVariable Long idEmpresa){
        List<Usuario> usuarios = usuarioRepository.listUserByEmpresa(idEmpresa);
        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }

}
