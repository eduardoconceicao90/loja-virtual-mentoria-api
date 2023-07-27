package com.eduardo.lojavirtual.model.dto.webMania;

import java.util.ArrayList;

public class LogDTO {

    private boolean bStat;
    private String versao;
    private String tpAmb;
    private String cStat;
    private String verAplic;
    private String xMotivo;
    private String dhRecbto;
    private String cUF;
    private String nRec;
    private ArrayList<WebManiaProtNotaFiscalDTO> aProt = new ArrayList<>();
    private String recibo;

    public boolean isbStat() {
        return bStat;
    }

    public void setbStat(boolean bStat) {
        this.bStat = bStat;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getTpAmb() {
        return tpAmb;
    }

    public void setTpAmb(String tpAmb) {
        this.tpAmb = tpAmb;
    }

    public String getcStat() {
        return cStat;
    }

    public void setcStat(String cStat) {
        this.cStat = cStat;
    }

    public String getVerAplic() {
        return verAplic;
    }

    public void setVerAplic(String verAplic) {
        this.verAplic = verAplic;
    }

    public String getxMotivo() {
        return xMotivo;
    }

    public void setxMotivo(String xMotivo) {
        this.xMotivo = xMotivo;
    }

    public String getDhRecbto() {
        return dhRecbto;
    }

    public void setDhRecbto(String dhRecbto) {
        this.dhRecbto = dhRecbto;
    }

    public String getcUF() {
        return cUF;
    }

    public void setcUF(String cUF) {
        this.cUF = cUF;
    }

    public String getnRec() {
        return nRec;
    }

    public void setnRec(String nRec) {
        this.nRec = nRec;
    }

    public ArrayList<WebManiaProtNotaFiscalDTO> getaProt() {
        return aProt;
    }

    public void setaProt(ArrayList<WebManiaProtNotaFiscalDTO> aProt) {
        this.aProt = aProt;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }
}
