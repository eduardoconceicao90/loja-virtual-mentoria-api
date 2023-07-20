package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.model.BoletoAsaas;
import com.eduardo.lojavirtual.model.dto.asaas.notificacaoPagamento.NotificacaoPagamentoApiAsaasDTO;
import com.eduardo.lojavirtual.repository.BoletoAsaasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        BoletoAsaas boletoAsaas = boletoAsaasRepository.findByCode(notificacaoPagamentoApiAsaas.idFatura());

        if (boletoAsaas == null) {
            return new ResponseEntity<String>("Boleto/Fatura não encontrada no banco de dados", HttpStatus.OK);
        }

        if (boletoAsaas != null && notificacaoPagamentoApiAsaas.boletoPixFaturaPaga() && !boletoAsaas.isQuitado()) {

            boletoAsaasRepository.quitarBoletoById(boletoAsaas.getId());
            System.out.println("Boleto: " + boletoAsaas.getCode() + " foi quitado");

            /* Fazendo qualquer regra de negocio que vc queira */

            return new ResponseEntity<String>("Recebido do Asaas, boleto id: " + boletoAsaas.getId(),HttpStatus.OK);
        }else {
            System.out.println("Fatura: "
                            + notificacaoPagamentoApiAsaas.idFatura()
                            + " não foi processada, quitada: "
                            + notificacaoPagamentoApiAsaas.boletoPixFaturaPaga()
                            + " valor quitado: "+ boletoAsaas.isQuitado());
        }

        return new ResponseEntity<String>("Não foi processado a fatura: " + notificacaoPagamentoApiAsaas.idFatura(), HttpStatus.OK);
    }
}
