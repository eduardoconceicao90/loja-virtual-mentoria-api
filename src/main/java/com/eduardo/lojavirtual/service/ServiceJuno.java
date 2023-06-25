package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.AccessTokenJunoAPI;
import com.eduardo.lojavirtual.model.BoletoJuno;
import com.eduardo.lojavirtual.model.VendaCompraLojaVirtual;
import com.eduardo.lojavirtual.model.dto.juno.BoletoGeradoApiJunoDTO;
import com.eduardo.lojavirtual.model.dto.juno.CobrancaJunoAPIDTO;
import com.eduardo.lojavirtual.model.dto.juno.ConteudoBoletoJunoDTO;
import com.eduardo.lojavirtual.model.dto.juno.ObjetoPostCarneJunoDTO;
import com.eduardo.lojavirtual.model.dto.juno.notificacaoPagamento.CriarWebHookDTO;
import com.eduardo.lojavirtual.repository.AccessTokenJunoRepository;
import com.eduardo.lojavirtual.repository.BoletoJunoRepository;
import com.eduardo.lojavirtual.repository.VendaCompraLojaVirtualRepository;
import com.eduardo.lojavirtual.util.TokenIntegracao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ServiceJuno {

    @Autowired
    private AccessTokenJunoService accessTokenJunoService;

    @Autowired
    private AccessTokenJunoRepository accessTokenJunoRepository;

    @Autowired
    private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;

    @Autowired
    private BoletoJunoRepository boletoJunoRepository;

    public String gerarCarneApi(ObjetoPostCarneJunoDTO objetoPostCarneJuno) throws Exception {

        VendaCompraLojaVirtual vendaCompraLojaVirtual = vendaCompraLojaVirtualRepository.findById(objetoPostCarneJuno.getIdVenda()).get();

        CobrancaJunoAPIDTO cobrancaJunoAPI = new CobrancaJunoAPIDTO();

        cobrancaJunoAPI.getCharge().setPixKey(TokenIntegracao.CHAVE_BOLETO_PIX);
        cobrancaJunoAPI.getCharge().setDescription(objetoPostCarneJuno.getDescription());
        cobrancaJunoAPI.getCharge().setAmount(Float.valueOf(objetoPostCarneJuno.getTotalAmount()));
        cobrancaJunoAPI.getCharge().setInstallments(Integer.parseInt(objetoPostCarneJuno.getInstallments()));

        Calendar dataVencimento = Calendar.getInstance();
        dataVencimento.add(Calendar.DAY_OF_MONTH, 7);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cobrancaJunoAPI.getCharge().setDueDate(dateFormat.format(dataVencimento.getTime()));

        cobrancaJunoAPI.getCharge().setFine(BigDecimal.valueOf(1.00));
        cobrancaJunoAPI.getCharge().setInterest(BigDecimal.valueOf(1.00));
        cobrancaJunoAPI.getCharge().setMaxOverdueDays(10);
        cobrancaJunoAPI.getCharge().getPaymentTypes().add("BOLETO_PIX");

        cobrancaJunoAPI.getBilling().setName(objetoPostCarneJuno.getPayerName());
        cobrancaJunoAPI.getBilling().setDocument(objetoPostCarneJuno.getPayerCpfCnpj());
        cobrancaJunoAPI.getBilling().setEmail(objetoPostCarneJuno.getEmail());
        cobrancaJunoAPI.getBilling().setPhone(objetoPostCarneJuno.getPayerPhone());

        AccessTokenJunoAPI accessTokenJunoAPI = this.obterTokenApiJuno();
        if (accessTokenJunoAPI != null) {

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

            if (clientResponse.getStatus() == 200) { /* Retornou com sucesso */

                clientResponse.close();
                objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY); /* Converte relacionamento um para muitos dentro desse json */

                BoletoGeradoApiJunoDTO jsonRetornoObj = objectMapper.readValue(stringRetorno,
                        new TypeReference<BoletoGeradoApiJunoDTO>() {});

                int recorrencia = 1;

                List<BoletoJuno> boletoJunos = new ArrayList<BoletoJuno>();

                for (ConteudoBoletoJunoDTO c : jsonRetornoObj.get_embedded().getCharges()) {

                    BoletoJuno boletoJuno = new BoletoJuno();

                    boletoJuno.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
                    boletoJuno.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
                    boletoJuno.setCode(c.getCode());
                    boletoJuno.setLink(c.getLink());
                    boletoJuno.setDataVencimento(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(c.getDueDate())));
                    boletoJuno.setCheckoutUrl(c.getCheckoutUrl());
                    boletoJuno.setValor(new BigDecimal(c.getAmount()));
                    boletoJuno.setIdChrBoleto(c.getId());
                    boletoJuno.setInstallmentLink(c.getInstallmentLink());
                    boletoJuno.setIdPix(c.getPix().getId());
                    boletoJuno.setPayloadInBase64(c.getPix().getPayloadInBase64());
                    boletoJuno.setImageInBase64(c.getPix().getImageInBase64());
                    boletoJuno.setRecorrencia(recorrencia);

                    boletoJunos.add(boletoJuno);
                    recorrencia ++;
                }

                boletoJunoRepository.saveAllAndFlush(boletoJunos);

                return boletoJunos.get(0).getLink();

            }else {
                return stringRetorno;
            }

        }else {
            return "Não existe chave de acesso para a API";
        }
    }

    public String gerarChaveBoletoPix() throws Exception {

        AccessTokenJunoAPI accessTokenJunoAPI = this.obterTokenApiJuno();

        Client client = new HostIgnoringClient("https://api.juno.com.br/").hostIgnoringClient();
        WebResource webResource = client.resource("https://api.juno.com.br/pix/keys");
        //WebResource webResource = client.resource("https://api.juno.com.br/api-integration/pix/keys");

        ClientResponse clientResponse = webResource
                .accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json")
                .header("X-API-Version", 2)
                .header("X-Resource-Token", TokenIntegracao.TOKEN_PRIVATE_JUNO)
                .header("Authorization", "Bearer " + accessTokenJunoAPI.getAccess_token())
                //.header("X-Idempotency-Key", "chave-boleto-pix")
                .post(ClientResponse.class, "{ \"type\": \"RANDOM_KEY\" }");

        return clientResponse.getEntity(String.class);
    }

    public AccessTokenJunoAPI obterTokenApiJuno() throws Exception {

        AccessTokenJunoAPI accessTokenJunoAPI = accessTokenJunoService.buscaTokenAtivo();

        if (accessTokenJunoAPI == null || (accessTokenJunoAPI != null && accessTokenJunoAPI.expirado()) ) {

            String clienteID = "Colar clienteID";
            String secretID = "Colar secretID";

            Client client = new HostIgnoringClient("https://api.juno.com.br/").hostIgnoringClient();

            WebResource webResource = client.resource("https://api.juno.com.br/authorization-server/oauth/token?grant_type=client_credentials");

            String basicChave = clienteID + ":" + secretID;
            String token_autorizacao = DatatypeConverter.printBase64Binary(basicChave.getBytes());

            ClientResponse clientResponse = webResource
                    .accept(MediaType.APPLICATION_FORM_URLENCODED)
                    .type(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Basic " + token_autorizacao)
                    .post(ClientResponse.class);

            if (clientResponse.getStatus() == 200) { /* Sucesso */
                accessTokenJunoRepository.deleteAll();
                accessTokenJunoRepository.flush();

                AccessTokenJunoAPI accessTokenJunoAPI2 = clientResponse.getEntity(AccessTokenJunoAPI.class);
                accessTokenJunoAPI2.setToken_acesso(token_autorizacao);

                accessTokenJunoAPI2 = accessTokenJunoRepository.saveAndFlush(accessTokenJunoAPI2);

                return accessTokenJunoAPI2;
            }else {
                return null;
            }

        }else {
            return accessTokenJunoAPI;
        }
    }

    public String cancelarBoleto(String code) throws Exception {

        AccessTokenJunoAPI accessTokenJunoAPI = this.obterTokenApiJuno();

        Client client = new HostIgnoringClient("https://api.juno.com.br/").hostIgnoringClient();
        WebResource webResource = client.resource("https://api.juno.com.br/charges/"+code+"/cancelation");

        ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
                .header("X-Api-Version", 2)
                .header("X-Resource-Token", TokenIntegracao.TOKEN_PRIVATE_JUNO)
                .header("Authorization", "Bearer " + accessTokenJunoAPI.getAccess_token())
                .put(ClientResponse.class);

        if (clientResponse.getStatus() == 204) {
            return "Cancelado com sucesso";
        }
        return clientResponse.getEntity(String.class);
    }

    public String criarWebHook(CriarWebHookDTO criarWebHook) throws Exception {

        AccessTokenJunoAPI accessTokenJunoAPI = this.obterTokenApiJuno();

        Client client = new HostIgnoringClient("https://api.juno.com.br/").hostIgnoringClient();
        WebResource webResource = client.resource("https://api.juno.com.br/notifications/webhooks");

        String json = new ObjectMapper().writeValueAsString(criarWebHook);

        ClientResponse clientResponse = webResource
                .accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json")
                .header("X-API-Version", 2)
                .header("X-Resource-Token", TokenIntegracao.TOKEN_PRIVATE_JUNO)
                .header("Authorization", "Bearer " + accessTokenJunoAPI.getAccess_token())
                .post(ClientResponse.class, json);

        String resposta = clientResponse.getEntity(String.class);
        clientResponse.close();

        return resposta;

    }

    public String listaWebHook() throws Exception {

        AccessTokenJunoAPI accessTokenJunoAPI = this.obterTokenApiJuno();

        Client client = new HostIgnoringClient("https://api.juno.com.br/").hostIgnoringClient();
        WebResource webResource = client.resource("https://api.juno.com.br/notifications/webhooks");

        ClientResponse clientResponse = webResource
                .accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json")
                .header("X-API-Version", 2)
                .header("X-Resource-Token", TokenIntegracao.TOKEN_PRIVATE_JUNO)
                .header("Authorization", "Bearer " + accessTokenJunoAPI.getAccess_token())
                .get(ClientResponse.class);

        String resposta = clientResponse.getEntity(String.class);

        return resposta;

    }

    public void deleteWebHook(String idWebHook) throws Exception {

        AccessTokenJunoAPI accessTokenJunoAPI = this.obterTokenApiJuno();

        Client client = new HostIgnoringClient("https://api.juno.com.br/").hostIgnoringClient();
        WebResource webResource = client.resource("https://api.juno.com.br/notifications/webhooks/" + idWebHook);

        webResource
                .accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json")
                .header("X-API-Version", 2)
                .header("X-Resource-Token", TokenIntegracao.TOKEN_PRIVATE_JUNO)
                .header("Authorization", "Bearer " + accessTokenJunoAPI.getAccess_token())
                .delete();
    }
}
