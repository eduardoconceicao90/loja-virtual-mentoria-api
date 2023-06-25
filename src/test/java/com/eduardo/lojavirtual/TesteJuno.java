package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.model.dto.juno.notificacaoPagamento.CriarWebHookDTO;
import com.eduardo.lojavirtual.service.ServiceJuno;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TesteJuno extends TestCase {

    @Autowired
    private ServiceJuno serviceJuno;

    @Test
    public void testeCriarWebHook() throws Exception {

        CriarWebHookDTO criarWebHook = new CriarWebHookDTO();
        criarWebHook.setUrl("meu_host/loja_virtual/requisicaojunoboleto/notificacaoapiv2");
        criarWebHook.getEventTypes().add("PAYMENT_NOTIFICATION");
        criarWebHook.getEventTypes().add("BILL_PAYMENT_STATUS_CHANGED");

        String retorno = serviceJuno.criarWebHook(criarWebHook);

        System.out.println(retorno);
    }
}
