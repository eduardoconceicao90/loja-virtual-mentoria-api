package com.eduardo.lojavirtual.model.dto.webMania;

public class ObjetoEmissaoNotaFiscalWebManiaDTO {

    private String uuid;
    private String status;
    private String motivo;
    private String nfe;
    private String serie;
    private String chave;
    private String modelo;
    private String xml;
    private String danfe;
    private String danfe_simples;
    private String danfe_etiqueta;
    private LogDTO LogObject;
    private float recibo;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNfe() {
        return nfe;
    }

    public void setNfe(String nfe) {
        this.nfe = nfe;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getDanfe() {
        return danfe;
    }

    public void setDanfe(String danfe) {
        this.danfe = danfe;
    }

    public String getDanfe_simples() {
        return danfe_simples;
    }

    public void setDanfe_simples(String danfe_simples) {
        this.danfe_simples = danfe_simples;
    }

    public String getDanfe_etiqueta() {
        return danfe_etiqueta;
    }

    public void setDanfe_etiqueta(String danfe_etiqueta) {
        this.danfe_etiqueta = danfe_etiqueta;
    }

    public LogDTO getLogObject() {
        return LogObject;
    }

    public void setLogObject(LogDTO logObject) {
        LogObject = logObject;
    }

    public float getRecibo() {
        return recibo;
    }

    public void setRecibo(float recibo) {
        this.recibo = recibo;
    }
}
