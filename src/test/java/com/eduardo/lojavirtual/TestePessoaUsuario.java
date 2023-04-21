package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.controller.PessoaController;
import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.PessoaJuridica;
import com.eduardo.lojavirtual.repository.PessoaRepository;
import com.eduardo.lojavirtual.service.PessoaUserService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestePessoaUsuario extends TestCase {

    @Autowired
    private PessoaController pessoaController;

    @Test
    public void testCadPessoaJuridica() throws ExceptionMentoriaJava {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
        pessoaJuridica.setNome("Teste");
        pessoaJuridica.setEmail("teste@mail.com");
        pessoaJuridica.setTelefone("81998855569");
        pessoaJuridica.setInsEstadual("65556565656665");
        pessoaJuridica.setInsMunicipal("55554565656565");
        pessoaJuridica.setNomeFantasia("54556565665");
        pessoaJuridica.setRazaoSocial("4656656566");

        pessoaController.salvarPj(pessoaJuridica);

    }
}