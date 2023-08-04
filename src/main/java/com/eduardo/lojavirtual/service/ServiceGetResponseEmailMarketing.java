package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.dto.getResponse.CampanhaGetResponseDTO;
import com.eduardo.lojavirtual.model.dto.getResponse.LeadCampanhaGetResponseDTO;
import com.eduardo.lojavirtual.model.dto.getResponse.NewsLetterGetResponseDTO;
import com.eduardo.lojavirtual.model.dto.getResponse.ObjetoFromFieldIdGetResponseDTO;
import com.eduardo.lojavirtual.util.TokenIntegracao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public String enviaEmailApiGetResponse(String idCampanha, String nomeEmail, String msg) throws Exception {

        NewsLetterGetResponseDTO newsLetterGetResponse = new NewsLetterGetResponseDTO();
        newsLetterGetResponse.getSendSettings().getSelectedCampaigns().add(idCampanha); /* Campanha e lista de e-mail para qual será enviado o e-mail */
        newsLetterGetResponse.setSubject(nomeEmail);
        newsLetterGetResponse.setName(newsLetterGetResponse.getSubject());
        newsLetterGetResponse.getReplyTo().setFromFieldId("******"); /* ID email para resposta */
        newsLetterGetResponse.getFromField().setFromFieldId("******"); /* ID do e-mail do remetente */
        newsLetterGetResponse.getCampaign().setCampaignId("******"); /* Campanha de origem, campanha pai */

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate hoje = LocalDate.now();
        LocalDate amanha = hoje.plusDays(1);
        String dataEnvio = amanha.format(dateTimeFormatter);

        newsLetterGetResponse.setSendOn(dataEnvio + "T15:20:52-03:00");

        newsLetterGetResponse.getContent().setHtml(msg);

        String json = new ObjectMapper().writeValueAsString(newsLetterGetResponse);

        Client client = new HostIgnoringClient(TokenIntegracao.URL_END_POINT_GET_RESPONSE).hostIgnoringClient();

        WebResource webResource = client.resource(TokenIntegracao.URL_END_POINT_GET_RESPONSE + "newsletters");

        ClientResponse clientResponse = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .header("X-Auth-Token", TokenIntegracao.TOKEN_GET_RESPONSE)
                .post(ClientResponse.class, json);

        String retorno = clientResponse.getEntity(String.class);

        if (clientResponse.getStatus() == 201) {
            retorno = "Enviado com sucesso";
        }

        clientResponse.close();

        return retorno;
    }

    public List<ObjetoFromFieldIdGetResponseDTO> listaFromFieldId() throws Exception{

        Client client = new HostIgnoringClient(TokenIntegracao.URL_END_POINT_GET_RESPONSE).hostIgnoringClient();

        WebResource webResource = client.resource(TokenIntegracao.URL_END_POINT_GET_RESPONSE + "from-fields");

        String clientResponse = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .header("X-Auth-Token", TokenIntegracao.TOKEN_GET_RESPONSE)
                .get(String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        List<ObjetoFromFieldIdGetResponseDTO> list = objectMapper.readValue(clientResponse,
                new TypeReference<List<ObjetoFromFieldIdGetResponseDTO>>() {});

        return list;
    }

}