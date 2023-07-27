package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.NotaFiscalVenda;
import com.eduardo.lojavirtual.model.VendaCompraLojaVirtual;
import com.eduardo.lojavirtual.model.dto.webMania.NotaFiscalEletronicaDTO;
import com.eduardo.lojavirtual.model.dto.webMania.ObjetoEmissaoNotaFiscalWebManiaDTO;
import com.eduardo.lojavirtual.repository.NotaFiscalVendaRepository;
import com.eduardo.lojavirtual.util.CredenciaisWebMania;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebManiaNotaFiscalService {

    @Autowired
    private NotaFiscalVendaRepository notaFiscalVendaRepository;

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

    public String cancelarNotaFiscal(String uuid, String motivo) throws Exception {

        Client client = new HostIgnoringClient("https://webmaniabr.com/api/").hostIgnoringClient();
        WebResource webResource = client.resource("https://webmaniabr.com/api/1/nfe/cancelar/");

        String json = "{\"uuid\":\""+uuid+"\", \"motivo\":\""+motivo+"\"}";

        ClientResponse clientResponse = webResource
                .accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("X-Consumer-Key", CredenciaisWebMania.CONSUMER_KEY)
                .header("X-Consumer-Secret", CredenciaisWebMania.CONSUMER_SECRET)
                .header("X-Access-Token", CredenciaisWebMania.ACCESS_TOKEN)
                .header("X-Access-Token-Secret", CredenciaisWebMania.ACCESS_TOKEN_SECRET)
                .put(ClientResponse.class, json);

        String stringRetorno = clientResponse.getEntity(String.class);

        return stringRetorno;
    }

    public String consultarNotaFiscal(String uuid) throws Exception {

        Client client = new HostIgnoringClient("https://webmaniabr.com/api/").hostIgnoringClient();
        WebResource webResource = client.resource("https://webmaniabr.com/api/1/nfe/consulta/");

        ClientResponse clientResponse = webResource.queryParam("uuid", uuid)
                .accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("X-Consumer-Key", CredenciaisWebMania.CONSUMER_KEY)
                .header("X-Consumer-Secret", CredenciaisWebMania.CONSUMER_SECRET)
                .header("X-Access-Token", CredenciaisWebMania.ACCESS_TOKEN)
                .header("X-Access-Token-Secret", CredenciaisWebMania.ACCESS_TOKEN_SECRET)
                .get(ClientResponse.class);

        String stringRetorno = clientResponse.getEntity(String.class);

        return stringRetorno;
    }

    public NotaFiscalVenda gravaNotaParaVenda(ObjetoEmissaoNotaFiscalWebManiaDTO emissaoNotaFiscalWebMania, VendaCompraLojaVirtual vendaCompraLojaVirtual) {

        NotaFiscalVenda notaFiscalVendaBusca = notaFiscalVendaRepository.buscaNotaPorVendaUnica(vendaCompraLojaVirtual.getId());

        NotaFiscalVenda notaFiscalVenda = new NotaFiscalVenda();

        if (notaFiscalVendaBusca != null && notaFiscalVendaBusca.getId() > 0) {
            notaFiscalVenda.setId(notaFiscalVendaBusca.getId());
        }

        notaFiscalVenda.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        notaFiscalVenda.setNumero(emissaoNotaFiscalWebMania.getUuid());
        notaFiscalVenda.setPdf(emissaoNotaFiscalWebMania.getDanfe());
        notaFiscalVenda.setSerie(emissaoNotaFiscalWebMania.getSerie());
        notaFiscalVenda.setTipo(emissaoNotaFiscalWebMania.getModelo());
        notaFiscalVenda.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
        notaFiscalVenda.setXml(emissaoNotaFiscalWebMania.getXml());
        notaFiscalVenda.setChave(emissaoNotaFiscalWebMania.getChave());

        return notaFiscalVendaRepository.saveAndFlush(notaFiscalVenda);

    }
}
