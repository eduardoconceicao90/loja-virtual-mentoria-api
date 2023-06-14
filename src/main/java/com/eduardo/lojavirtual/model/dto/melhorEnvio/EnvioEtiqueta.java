package com.eduardo.lojavirtual.model.dto.melhorEnvio;

import java.util.ArrayList;
import java.util.List;

public class EnvioEtiqueta {

    private String service;
    private String agency;

    private FromEnvioEtiqueta from = new FromEnvioEtiqueta();

    private ToEnvioEtiqueta to = new ToEnvioEtiqueta();

    private List<ProductsEnvioEtiqueta> products = new ArrayList<ProductsEnvioEtiqueta>();

    private List<VolumesEnvioEtiqueta> volumes = new ArrayList<VolumesEnvioEtiqueta>();

    private OptionsEnvioEtiqueta options = new OptionsEnvioEtiqueta();

    public void setOptions(OptionsEnvioEtiqueta options) {
        this.options = options;
    }

    public OptionsEnvioEtiqueta getOptions() {
        return options;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public FromEnvioEtiqueta getFrom() {
        return from;
    }

    public void setFrom(FromEnvioEtiqueta from) {
        this.from = from;
    }

    public ToEnvioEtiqueta getTo() {
        return to;
    }

    public void setTo(ToEnvioEtiqueta to) {
        this.to = to;
    }

    public List<ProductsEnvioEtiqueta> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsEnvioEtiqueta> products) {
        this.products = products;
    }

    public List<VolumesEnvioEtiqueta> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumesEnvioEtiqueta> volumes) {
        this.volumes = volumes;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }
}
