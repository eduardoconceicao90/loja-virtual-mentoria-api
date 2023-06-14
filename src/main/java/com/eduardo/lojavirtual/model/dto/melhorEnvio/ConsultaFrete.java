package com.eduardo.lojavirtual.model.dto.melhorEnvio;

import java.util.ArrayList;
import java.util.List;

public class ConsultaFrete {

    private From from;
    private To to;
    private List<Products> products = new ArrayList<Products>();

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
