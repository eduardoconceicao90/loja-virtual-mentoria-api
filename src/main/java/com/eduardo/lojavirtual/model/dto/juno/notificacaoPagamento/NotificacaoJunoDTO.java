package com.eduardo.lojavirtual.model.dto.juno.notificacaoPagamento;

import com.eduardo.lojavirtual.model.dto.juno.notificacaoPagamento.DataNotificacaoJunoDTO;

import java.util.ArrayList;
import java.util.List;

/* Objeto principal recebimento (pagamento) api juno boleto pix - webhook */
public class NotificacaoJunoDTO {

    private String eventId;
    private String eventType;
    private String timestamp;

    private List<DataNotificacaoJunoDTO> data = new ArrayList<DataNotificacaoJunoDTO>();

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<DataNotificacaoJunoDTO> getData() {
        return data;
    }

    public void setData(List<DataNotificacaoJunoDTO> data) {
        this.data = data;
    }
}
