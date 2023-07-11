package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.service.ServiceAsaas;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TesteAsaas {

    @Autowired
    private ServiceAsaas serviceAsaas;

    @Test
    public void testeCriarChavePixAsaas() throws Exception {
        String chaveApi = serviceAsaas.criarChavePixAsaas();
        System.out.println("Chave Asaas API: " + chaveApi);
    }
}
