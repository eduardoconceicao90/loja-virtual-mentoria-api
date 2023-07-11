package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.model.dto.asaas.ObjetoPostCarneAssasDTO;
import com.eduardo.lojavirtual.service.ServiceAsaas;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TesteAsaas extends TestCase {

    @Autowired
    private ServiceAsaas serviceAsaas;

    @Test
    public void testeCriarChavePixAsaas() throws Exception {
        String chaveApi = serviceAsaas.criarChavePixAsaas();
        System.out.println("Chave Asaas API: " + chaveApi);
    }

    @Test
    public void testeBuscaClientePessoaApiAsaas() throws Exception {
        ObjetoPostCarneAssasDTO dados = new ObjetoPostCarneAssasDTO();
        dados.setEmail("eduardosaconceicao@gmail.com");
        dados.setPayerName("Eduardo Conceição");
        dados.setPayerCpfCnpj("05217956569");
        dados.setPayerPhone("81983784049");

        String  customer_id = serviceAsaas.buscaClientePessoaApiAsaas(dados);

        assertEquals("cus_000058875584", customer_id);
    }
}
