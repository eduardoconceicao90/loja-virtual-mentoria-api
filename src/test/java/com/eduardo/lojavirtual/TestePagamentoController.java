package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.controller.PagamentoController;
import com.eduardo.lojavirtual.controller.RecebePagamentoWebHookApiAsaas;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestePagamentoController {

    @Autowired
    private PagamentoController pagamentoController;

    @Autowired
    private RecebePagamentoWebHookApiAsaas recebePagamentoWebHookApiAsaas;

    @Autowired
    private WebApplicationContext wac;

    @Test
    public void testefinalizarCompraCartaoAsaas() throws Exception {
        pagamentoController.finalizarCompraCartaoAsaas("********************", "Eduardo Conceicao",
                                                    "***", "**",
                                                    "****", 25L, "05217956569",
                                                    1, "54768130", "Rua Teixeira Soares",
                                                    "303", "PE", "Camaragibe");
    }

    @Test
    public void testeRecebeNotificacaoPagamentoApiAsaas() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        MockMvc mockMvc = builder.build();

        String json = new String(Files.readAllBytes(Paths.get("C:\\******************\\loja-virtual-mentoria-api\\src\\test\\java\\com\\eduardo\\lojavirtual\\jsonwebhookasaas.txt")));

        ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.post("/requisicaoasaasboleto/notificacaoapiasaas")
                .content(json)
                .accept("application/json;charset=UTF-8")
                .contentType("application/json;charset=UTF-8"));

        System.out.println(retornoApi.andReturn().getRequest().getContentAsString());
    }
}
