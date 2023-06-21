package com.eduardo.lojavirtual.model.dto.juno.notificacaoPagamento;

import com.eduardo.lojavirtual.model.dto.juno.notificacaoPagamento.AttributesDataNotificacaoJunoDTO;

public class DataNotificacaoJunoDTO {

    private String entityId;
    private String entityType;

    private AttributesDataNotificacaoJunoDTO attributes = new AttributesDataNotificacaoJunoDTO();

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public AttributesDataNotificacaoJunoDTO getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesDataNotificacaoJunoDTO attributes) {
        this.attributes = attributes;
    }
}
