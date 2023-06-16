package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.AccessTokenJunoAPI;
import com.eduardo.lojavirtual.repository.AccessTokenJunoRepository;
import com.eduardo.lojavirtual.util.TokenIntegracao;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

@Service
public class ServiceJuno {

    @Autowired
    private AccessTokenJunoService accessTokenJunoService;

    @Autowired
    private AccessTokenJunoRepository accessTokenJunoRepository;

    public String geraChaveBoletoPix() throws Exception {

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
}
