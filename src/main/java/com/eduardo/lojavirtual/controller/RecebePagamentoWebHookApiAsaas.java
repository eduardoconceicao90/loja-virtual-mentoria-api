package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.model.dto.asaas.notificacaoPagamento.NotificacaoPagamentoApiAsaasDTO;
import com.eduardo.lojavirtual.repository.BoletoAsaasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/requisicaoasaasboleto")
public class RecebePagamentoWebHookApiAsaas {

    @Autowired
    private BoletoAsaasRepository boletoAsaasRepository;

    @ResponseBody
    @RequestMapping(value = "/notificacaoapiasaas", consumes = {"application/json;charset=UTF-8"},
            headers = "Content-Type=application/json;charset=UTF-8", method = RequestMethod.POST)
    public ResponseEntity<String> recebeNotificacaoPagamentoApiAsaas(@RequestBody NotificacaoPagamentoApiAsaasDTO notificacaoPagamentoApiAsaas) {

       return null;
    }
}
