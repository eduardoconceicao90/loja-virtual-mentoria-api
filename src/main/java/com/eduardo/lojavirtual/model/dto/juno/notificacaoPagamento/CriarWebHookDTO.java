package com.eduardo.lojavirtual.model.dto.juno.notificacaoPagamento;

import java.util.ArrayList;
import java.util.List;

public class CriarWebHookDTO {

    private String url;

    private List<String> eventTypes = new ArrayList<String>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<String> eventTypes) {
        this.eventTypes = eventTypes;
    }

}
