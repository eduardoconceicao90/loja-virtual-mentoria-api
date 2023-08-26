package com.eduardo.lojavirtual.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.Endereco;
import com.eduardo.lojavirtual.model.PessoaFisica;
import com.eduardo.lojavirtual.model.PessoaJuridica;
import com.eduardo.lojavirtual.model.Usuario;
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
            throw new ExceptionMentoriaJava("Cnpj : " + pessoaJuridica.getCnpj() + " está inválido.");
        }

        if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {

            for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {

                CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());

                pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
                pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
                pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
                pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDTO.getLogradouro());
                pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());

            }
        } else {

            for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {

                Endereco enderecoTemp =  enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();

                if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {

                    CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());

                    pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
                    pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
                    pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
                    pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDTO.getLogradouro());
                    pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
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
    
    
}
