package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.model.BoletoJuno;
import com.eduardo.lojavirtual.model.dto.juno.notificacaoPagamento.DataNotificacaoJunoDTO;
import com.eduardo.lojavirtual.model.dto.juno.notificacaoPagamento.NotificacaoJunoDTO;
import com.eduardo.lojavirtual.repository.BoletoJunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/requisicaojunoboleto")
public class RecebePagamentoWebHookApiJuno {

    @Autowired
    private BoletoJunoRepository boletoJunoRepository;

    @ResponseBody
    @RequestMapping(value = "/notificacaoapiv2", consumes = {"application/json;charset=UTF-8"},
            headers = "Content-Type=application/json;charset=UTF-8", method = RequestMethod.POST)
    private HttpStatus recebeNotificaopagamentojunoapiv2(@RequestBody NotificacaoJunoDTO notificacaoJunoDTO) {

        for (DataNotificacaoJunoDTO data : notificacaoJunoDTO.getData()) {

            String codigoBoletoPix = data.getAttributes().getCharge().getCode();

            String status = data.getAttributes().getStatus();

            boolean boletoPago = status.equalsIgnoreCase("CONFIRMED") ? true : false;

            BoletoJuno boletoJuno = boletoJunoRepository.findByCode(codigoBoletoPix);

            if (boletoJuno != null && !boletoJuno.isQuitado() && boletoPago) {

                boletoJunoRepository.quitarBoletoById(boletoJuno.getId());
                System.out.println("Boleto: " + boletoJuno.getCode() + " foi quitado ");

            }
        }

        return HttpStatus.OK;
    }
}
