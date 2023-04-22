package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.controller.PessoaController;
import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.Endereco;
import com.eduardo.lojavirtual.model.PessoaJuridica;
import com.eduardo.lojavirtual.model.enums.TipoEndereco;
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
        pessoaJuridica.setEmail("eduardosaconceicao@gmail.com");
        pessoaJuridica.setTelefone("81998855569");
        pessoaJuridica.setInsEstadual("65556565656665");
        pessoaJuridica.setInsMunicipal("55554565656565");
        pessoaJuridica.setNomeFantasia("54556565665");
        pessoaJuridica.setRazaoSocial("4656656566");

        Endereco endereco1 = new Endereco();
        endereco1.setBairro("Jd Dias");
        endereco1.setCep("556556565");
        endereco1.setComplemento("Casa cinza");
        endereco1.setEmpresa(pessoaJuridica);
        endereco1.setNumero("389");
        endereco1.setPessoa(pessoaJuridica);
        endereco1.setRuaLogra("Av. são joao sexto");
        endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
        endereco1.setUf("BA");
        endereco1.setCidade("Salvador");

        Endereco endereco2 = new Endereco();
        endereco2.setBairro("Jd Maracana");
        endereco2.setCep("7878778");
        endereco2.setComplemento("Andar 4");
        endereco2.setEmpresa(pessoaJuridica);
        endereco2.setNumero("555");
        endereco2.setPessoa(pessoaJuridica);
        endereco2.setRuaLogra("Av. maringá");
        endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
        endereco2.setUf("PR");
        endereco2.setCidade("Curitiba");

        pessoaJuridica.getEnderecos().add(endereco2);
        pessoaJuridica.getEnderecos().add(endereco1);

        pessoaJuridica = pessoaController.salvarPj(pessoaJuridica).getBody();

        assertEquals(true, pessoaJuridica.getId() > 0 );

        for (Endereco endereco : pessoaJuridica.getEnderecos()) {
            assertEquals(true, endereco.getId() > 0);
        }

        assertEquals(2, pessoaJuridica.getEnderecos().size());

    }
}