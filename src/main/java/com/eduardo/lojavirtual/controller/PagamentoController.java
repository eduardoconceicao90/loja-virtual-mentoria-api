package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.model.AccessTokenJunoAPI;
import com.eduardo.lojavirtual.model.VendaCompraLojaVirtual;
import com.eduardo.lojavirtual.model.dto.VendaCompraLojaVirtualDTO;
import com.eduardo.lojavirtual.model.dto.juno.CobrancaJunoAPIDTO;
import com.eduardo.lojavirtual.model.dto.juno.ErroResponseDTO;
import com.eduardo.lojavirtual.repository.VendaCompraLojaVirtualRepository;
import com.eduardo.lojavirtual.service.HostIgnoringClient;
import com.eduardo.lojavirtual.service.ServiceJuno;
import com.eduardo.lojavirtual.service.VendaService;
import com.eduardo.lojavirtual.util.TokenIntegracao;
import com.eduardo.lojavirtual.util.ValidaCPF;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
public class PagamentoController {

    @Autowired
    private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;

    @Autowired
    private VendaService vendaService;

    @Autowired
    private ServiceJuno serviceJuno;

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
                                                        @RequestParam("cidade") String cidade) throws Exception {

        VendaCompraLojaVirtual vendaCompraLojaVirtual = vendaCompraLojaVirtualRepository.findById(idVendaCampo).orElse(null);

        if (vendaCompraLojaVirtual == null) {
            return new ResponseEntity<String>("Código da venda não existe!", HttpStatus.OK);
        }

        String cpfLimpo =  cpf.replaceAll("\\.", "").replaceAll("\\-", "");

        if (!ValidaCPF.isCPF(cpfLimpo)) {
            return new ResponseEntity<String>("CPF informado é inválido.", HttpStatus.OK);
        }

        if (qtdparcela > 12 || qtdparcela <= 0) {
            return new ResponseEntity<String>("Quantidade de parcelar deve ser de 1 até 12.", HttpStatus.OK);
        }

        if (vendaCompraLojaVirtual.getValorTotal().doubleValue() <= 0) {
            return new ResponseEntity<String>("Valor da venda não pode ser Zero(0).", HttpStatus.OK);
        }

        AccessTokenJunoAPI accessTokenJunoAPI = serviceJuno.obterTokenApiJuno();

        if (accessTokenJunoAPI == null) {
            return new ResponseEntity<String>("Autorização bancária não foi encontrada.", HttpStatus.OK);
        }

        CobrancaJunoAPIDTO cobrancaJunoAPI = new CobrancaJunoAPIDTO();
        cobrancaJunoAPI.getCharge().setPixKey(TokenIntegracao.CHAVE_BOLETO_PIX);
        cobrancaJunoAPI.getCharge().setDescription("Pagamento da venda: " + vendaCompraLojaVirtual.getId() + " para o cliente: " + vendaCompraLojaVirtual.getPessoa().getNome());

        if (qtdparcela == 1) {
            cobrancaJunoAPI.getCharge().setAmount(vendaCompraLojaVirtual.getValorTotal().floatValue());
        }else {
            BigDecimal valorParcela = vendaCompraLojaVirtual.getValorTotal().divide(BigDecimal.valueOf(qtdparcela), RoundingMode.DOWN).setScale(2, RoundingMode.DOWN);
            cobrancaJunoAPI.getCharge().setAmount(valorParcela.floatValue());
        }

        cobrancaJunoAPI.getCharge().setInstallments(qtdparcela);

        Calendar dataVencimento = Calendar.getInstance();
        dataVencimento.add(Calendar.DAY_OF_MONTH, 7);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        cobrancaJunoAPI.getCharge().setDueDate(dateFormater.format(dataVencimento.getTime()));

        cobrancaJunoAPI.getCharge().setFine(BigDecimal.valueOf(1.00));
        cobrancaJunoAPI.getCharge().setInterest(BigDecimal.valueOf(1.00));
        cobrancaJunoAPI.getCharge().setMaxOverdueDays(7);
        cobrancaJunoAPI.getCharge().getPaymentTypes().add("CREDIT_CARD");

        cobrancaJunoAPI.getBilling().setName(holderName);
        cobrancaJunoAPI.getBilling().setDocument(cpfLimpo);
        cobrancaJunoAPI.getBilling().setEmail(vendaCompraLojaVirtual.getPessoa().getEmail());
        cobrancaJunoAPI.getBilling().setPhone(vendaCompraLojaVirtual.getPessoa().getTelefone());

        Client client = new HostIgnoringClient("https://api.juno.com.br/").hostIgnoringClient();
        WebResource webResource = client.resource("https://api.juno.com.br/charges");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cobrancaJunoAPI);

        ClientResponse clientResponse = webResource
                .accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("X-API-Version", 2)
                .header("X-Resource-Token", TokenIntegracao.TOKEN_PRIVATE_JUNO)
                .header("Authorization", "Bearer " + accessTokenJunoAPI.getAccess_token())
                .post(ClientResponse.class, json);

        String stringRetorno = clientResponse.getEntity(String.class);

        if (clientResponse.getStatus() != 200) {
            ErroResponseDTO jsonRetornoErro = objectMapper.readValue(stringRetorno, new TypeReference<ErroResponseDTO>() {});
            return new ResponseEntity<String>(jsonRetornoErro.listaErro(), HttpStatus.OK);
        }

        clientResponse.close();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
