package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.model.dto.melhorEnvio.EmpresaTransporteDTO;
import com.eduardo.lojavirtual.util.TokenIntegracao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TesteAPIMelhorEnvio {

    public static void main(String[] args) throws Exception {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://sandbox.melhorenvio.com.br/api/v2/me/shipment/companies")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("User-Agent", "eduardodevjavaweb@gmail.com")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}
