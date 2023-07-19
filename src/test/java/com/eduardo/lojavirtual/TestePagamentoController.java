package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.controller.PagamentoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestePagamentoController {

    @Autowired
    private PagamentoController pagamentoController;

    @Test
    public void testefinalizarCompraCartaoAsaas() throws Exception {
        pagamentoController.finalizarCompraCartaoAsaas("********************", "Eduardo Conceicao",
                                                    "***", "**",
                                                    "****", 25L, "05217956569",
                                                    1, "54768130", "Rua Teixeira Soares",
                                                    "303", "PE", "Camaragibe");
    }
}
