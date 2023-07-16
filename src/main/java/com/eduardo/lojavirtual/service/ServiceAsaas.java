package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.BoletoAsaas;
import com.eduardo.lojavirtual.model.VendaCompraLojaVirtual;
import com.eduardo.lojavirtual.model.dto.asaas.*;
import com.eduardo.lojavirtual.repository.BoletoAsaasRepository;
import com.eduardo.lojavirtual.repository.VendaCompraLojaVirtualRepository;
import com.eduardo.lojavirtual.util.AsaasApiPagamentoStatus;
import com.eduardo.lojavirtual.util.ValidaCPF;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ServiceAsaas {

    @Autowired
    private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;

    @Autowired
    private BoletoAsaasRepository boletoAsaasRepository;

    /* Cria a chave da API Asass para o PIX */
    public String criarChavePixAsaas() throws Exception {

        Client client = new HostIgnoringClient(AsaasApiPagamentoStatus.URL_API_ASAAS).hostIgnoringClient();
        WebResource webResource = client.resource(AsaasApiPagamentoStatus.URL_API_ASAAS + "pix/addressKeys");

        ClientResponse clientResponse = webResource.accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json")
                .header("access_token", AsaasApiPagamentoStatus.API_KEY)
                .post(ClientResponse.class, "{\"type\":\"EVP\"}");

        String stringRetorno = clientResponse.getEntity(String.class);
        clientResponse.close();
        return stringRetorno;
    }

    /* Retorna o id do Customer (Pessoa/cliente) */
    public String buscaClientePessoaApiAsaas(ObjetoPostCarneAssasDTO dados) throws Exception {

        /* id do cliente para ligar com a cobrança */
        String customer_id = "";

        /* --------------INICIO - criando ou consultando o cliente */

        Client client = new HostIgnoringClient(AsaasApiPagamentoStatus.URL_API_ASAAS).hostIgnoringClient();
        WebResource webResource = client.resource(AsaasApiPagamentoStatus.URL_API_ASAAS + "customers?email="+dados.getEmail());

        ClientResponse clientResponse = webResource.accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json")
                .header("access_token", AsaasApiPagamentoStatus.API_KEY)
                .get(ClientResponse.class);

        LinkedHashMap<String, Object> parser = new JSONParser(clientResponse.getEntity(String.class)).parseObject();
        clientResponse.close();
        Integer total = Integer.parseInt(parser.get("totalCount").toString());

        if (total <= 0) { /* Cria o cliente */
            ClienteAsaasApiPagamentoDTO clienteAsaasApiPagamento = new ClienteAsaasApiPagamentoDTO();

            if (!ValidaCPF.isCPF(dados.getPayerCpfCnpj())) {
                clienteAsaasApiPagamento.setCpfCnpj("60051803046");
            }else {
                clienteAsaasApiPagamento.setCpfCnpj(dados.getPayerCpfCnpj());
            }

            clienteAsaasApiPagamento.setEmail(dados.getEmail());
            clienteAsaasApiPagamento.setName(dados.getPayerName());
            clienteAsaasApiPagamento.setPhone(dados.getPayerPhone());

            Client client2 = new HostIgnoringClient(AsaasApiPagamentoStatus.URL_API_ASAAS).hostIgnoringClient();
            WebResource webResource2 = client2.resource(AsaasApiPagamentoStatus.URL_API_ASAAS + "customers");

            ClientResponse clientResponse2 = webResource2.accept("application/json;charset=UTF-8")
                    .header("Content-Type", "application/json")
                    .header("access_token", AsaasApiPagamentoStatus.API_KEY)
                    .post(ClientResponse.class, new ObjectMapper().writeValueAsBytes(clienteAsaasApiPagamento));

            LinkedHashMap<String, Object> parser2 = new JSONParser(clientResponse2.getEntity(String.class)).parseObject();
            clientResponse2.close();

            customer_id = parser2.get("id").toString();

        }else { /* Já tem cliente cadastrado */
            List<Object> data = (List<Object>) parser.get("data");
            customer_id = new Gson().toJsonTree(data.get(0)).getAsJsonObject().get("id").toString().replaceAll("\"", "");
        }

        return customer_id;
    }

    public String gerarCarneApiAsaas(ObjetoPostCarneAssasDTO objetoPostCarneAssas) throws Exception {

        VendaCompraLojaVirtual vendaCompraLojaVirtual = vendaCompraLojaVirtualRepository.findById(objetoPostCarneAssas.getIdVenda()).get();

        CobrancaApiAsaasDTO cobrancaApiAsaas = new CobrancaApiAsaasDTO();
        cobrancaApiAsaas.setCustomer(this.buscaClientePessoaApiAsaas(objetoPostCarneAssas));

        /* PIX, BOLETO OU UNDEFINED */
        cobrancaApiAsaas.setBillingType("UNDEFINED"); /* Gerando tanto PIX quanto Boleto */
        cobrancaApiAsaas.setDescription("Pix ou Boleto gerado para cobrança, cód: " + vendaCompraLojaVirtual.getId());
        cobrancaApiAsaas.setInstallmentValue(vendaCompraLojaVirtual.getValorTotal().floatValue());
        cobrancaApiAsaas.setInstallmentCount(1);

        Calendar dataVencimento = Calendar.getInstance();
        dataVencimento.add(Calendar.DAY_OF_MONTH, 7);
        cobrancaApiAsaas.setDueDate(new SimpleDateFormat("yyyy-MM-dd").format(dataVencimento.getTime()));

        cobrancaApiAsaas.getInterest().setValue(1F);
        cobrancaApiAsaas.getFine().setValue(1F);

        String json  = new ObjectMapper().writeValueAsString(cobrancaApiAsaas);
        Client client = new HostIgnoringClient(AsaasApiPagamentoStatus.URL_API_ASAAS).hostIgnoringClient();
        WebResource webResource = client.resource(AsaasApiPagamentoStatus.URL_API_ASAAS + "payments");

        ClientResponse clientResponse = webResource
                .accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json")
                .header("access_token", AsaasApiPagamentoStatus.API_KEY)
                .post(ClientResponse.class, json);

        String stringRetorno = clientResponse.getEntity(String.class);
        clientResponse.close();

        return stringRetorno;

    }
}
