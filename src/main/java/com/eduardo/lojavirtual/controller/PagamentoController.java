package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.model.VendaCompraLojaVirtual;
import com.eduardo.lojavirtual.model.dto.VendaCompraLojaVirtualDTO;
import com.eduardo.lojavirtual.repository.VendaCompraLojaVirtualRepository;
import com.eduardo.lojavirtual.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PagamentoController {

    @Autowired
    private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;

    @Autowired
    private VendaService vendaService;

    @RequestMapping(method = RequestMethod.GET, value = "**/pagamento/{idVendaCompra}")
    public ModelAndView pagamento(@PathVariable(value = "idVendaCompra", required = false) String idVendaCompra) {

        VendaCompraLojaVirtual compraLojaVirtual = vendaCompraLojaVirtualRepository.findByIdExclusao(Long.parseLong(idVendaCompra));

        ModelAndView modelAndView = new ModelAndView("pagamento");

        if (compraLojaVirtual == null) {
            modelAndView.addObject("venda", new VendaCompraLojaVirtualDTO());
        }else {
            modelAndView.addObject("venda", vendaService.consultaVenda(compraLojaVirtual));
        }
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "**/finalizarCompraCartao")
    public ResponseEntity<String> finalizarCompraCartao(@RequestParam("cardHash") String cardHash,
                                                        @RequestParam("cardNumber") String cardNumber,
                                                        @RequestParam("holderName") String holderName,
                                                        @RequestParam("securityCode") String securityCode,
                                                        @RequestParam("expirationMonth") String expirationMonth,
                                                        @RequestParam("expirationYear") String expirationYear,
                                                        @RequestParam("idVendaCampo") Long idVendaCampo,
                                                        @RequestParam("cpf") String cpf,
                                                        @RequestParam("qtdparcela") Integer qtdparcela,
                                                        @RequestParam("cep") String cep,
                                                        @RequestParam("rua") String rua,
                                                        @RequestParam("numero") String numero,
                                                        @RequestParam("estado") String estado,
                                                        @RequestParam("cidade") String cidade) throws Exception{

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
