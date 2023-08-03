package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.dto.getResponse.CampanhaGetResponseDTO;
import com.eduardo.lojavirtual.model.dto.getResponse.LeadCampanhaGetResponseDTO;
import com.eduardo.lojavirtual.util.TokenIntegracao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import java.util.List;

@Service
public class ServiceGetResponseEmailMarketing {


    public List<CampanhaGetResponseDTO> carregaListaCampanhaGetResponse() throws Exception {

        Client client = new HostIgnoringClient(TokenIntegracao.URL_END_POINT_GET_RESPONSE).hostIgnoringClient();

        String json = client.resource(TokenIntegracao.URL_END_POINT_GET_RESPONSE + "campaigns")
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .header("X-Auth-Token", TokenIntegracao.TOKEN_GET_RESPONSE)
                .get(String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        List<CampanhaGetResponseDTO> list = objectMapper.readValue(json,
                new TypeReference<List<CampanhaGetResponseDTO>>() {});

        return list;
    }

    public String criaLeadApiGetResponse(LeadCampanhaGetResponseDTO leadCampanhaGetResponse) throws Exception {

        String json = new ObjectMapper().writeValueAsString(leadCampanhaGetResponse);

        Client client = new HostIgnoringClient(TokenIntegracao.URL_END_POINT_GET_RESPONSE).hostIgnoringClient();

        WebResource webResource = client.resource(TokenIntegracao.URL_END_POINT_GET_RESPONSE + "contacts");

        ClientResponse clientResponse = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .header("X-Auth-Token", TokenIntegracao.TOKEN_GET_RESPONSE)
                .post(ClientResponse.class, json);

        String retorno = clientResponse.getEntity(String.class);

        if (clientResponse.getStatus() == 202) {
            retorno = "Cadastrado com sucesso";
        }

        clientResponse.close();
        return retorno;
    }

}