package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.model.PessoaJuridica;
import com.eduardo.lojavirtual.repository.PessoaRepository;
import com.eduardo.lojavirtual.service.PessoaUserService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestePessoaUsuario extends TestCase {

    @Autowired
    private PessoaUserService pessoaUserService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    public void testCadPessoaJuridica() {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setCnpj("865545598956556");
        pessoaJuridica.setNome("Eduardo Conceição");
        pessoaJuridica.setEmail("eduardo@mail.com");
        pessoaJuridica.setTelefone("81998855569");
        pessoaJuridica.setInsEstatual("65556565656665");
        pessoaJuridica.setInsMunicipal("55554565656565");
        pessoaJuridica.setNomeFantasia("54556565665");
        pessoaJuridica.setRazaoSocial("4656656566");

        pessoaRepository.save(pessoaJuridica);

    }
}