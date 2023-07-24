package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.dto.webMania.NotaFiscalEletronicaDTO;
import com.eduardo.lojavirtual.util.CredenciaisWebMania;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Service;

@Service
public class WebManiaNotaFiscalService {

    public String emitirNotaFiscal(NotaFiscalEletronicaDTO notaFiscalEletronica) throws Exception {

        Client client = new HostIgnoringClient("https://webmaniabr.com/api/").hostIgnoringClient();
        WebResource webResource = client.resource("https://webmaniabr.com/api/1/nfe/emissao/");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(notaFiscalEletronica);

        ClientResponse clientResponse = webResource
                .accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("X-Consumer-Key", CredenciaisWebMania.CONSUMER_KEY)
                .header("X-Consumer-Secret", CredenciaisWebMania.CONSUMER_SECRET)
                .header("X-Access-Token", CredenciaisWebMania.ACCESS_TOKEN)
                .header("X-Access-Token-Secret", CredenciaisWebMania.ACCESS_TOKEN_SECRET)
                .post(ClientResponse.class, json);

        String stringRetorno = clientResponse.getEntity(String.class);

        return stringRetorno;
    }
}
