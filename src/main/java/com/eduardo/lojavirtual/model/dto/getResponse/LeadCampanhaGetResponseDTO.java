package com.eduardo.lojavirtual.model.dto.getResponse;

import java.util.ArrayList;
import java.util.List;

public class LeadCampanhaGetResponseDTO {

    private String name;
    private String email;
    private String dayOfCycle = "0";
    private String scoring;

    private LeadCampanhaGetResponseCadastroDTO campaign = new LeadCampanhaGetResponseCadastroDTO();

    private List<String> tags = new ArrayList<String>();

    private List<CustomFieldValuesGetResponseDTO> customFieldValues = new ArrayList<CustomFieldValuesGetResponseDTO>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDayOfCycle() {
        return dayOfCycle;
    }

    public void setDayOfCycle(String dayOfCycle) {
        this.dayOfCycle = dayOfCycle;
    }

    public String getScoring() {
        return scoring;
    }

    public void setScoring(String scoring) {
        this.scoring = scoring;
    }

    public LeadCampanhaGetResponseCadastroDTO getCampaign() {
        return campaign;
    }

    public void setCampaign(LeadCampanhaGetResponseCadastroDTO campaign) {
        this.campaign = campaign;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<CustomFieldValuesGetResponseDTO> getCustomFieldValues() {
        return customFieldValues;
    }

    public void setCustomFieldValues(List<CustomFieldValuesGetResponseDTO> customFieldValues) {
        this.customFieldValues = customFieldValues;
    }
}
