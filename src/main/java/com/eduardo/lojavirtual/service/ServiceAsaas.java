package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.dto.asaas.ObjetoPostCarneAssasDTO;
import com.eduardo.lojavirtual.util.AsaasApiPagamentoStatus;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class ServiceAsaas {

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

        }

        return customer_id;
    }
}
