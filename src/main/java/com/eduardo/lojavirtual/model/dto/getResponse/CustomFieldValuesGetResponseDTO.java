package com.eduardo.lojavirtual.model.dto.getResponse;

import java.util.ArrayList;
import java.util.List;

public class CustomFieldValuesGetResponseDTO {

    private String customFieldId;
    private List<String> value = new ArrayList<String>();

    public String getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(String customFieldId) {
        this.customFieldId = customFieldId;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
