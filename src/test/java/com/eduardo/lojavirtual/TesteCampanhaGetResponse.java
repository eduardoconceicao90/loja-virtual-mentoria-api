package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.model.dto.getResponse.CampanhaGetResponseDTO;
import com.eduardo.lojavirtual.model.dto.getResponse.LeadCampanhaGetResponseCadastroDTO;
import com.eduardo.lojavirtual.model.dto.getResponse.LeadCampanhaGetResponseDTO;
import com.eduardo.lojavirtual.service.ServiceGetResponseEmailMarketing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TesteCampanhaGetResponse {

    @Autowired
    private ServiceGetResponseEmailMarketing serviceGetResponseEmailMarketing;

    @Test
    public void testeCarregaCampanhaGetResponse() throws Exception {

        List<CampanhaGetResponseDTO> list = serviceGetResponseEmailMarketing.carregaListaCampanhaGetResponse();

        for (CampanhaGetResponseDTO campanhaGetResponse : list) {
            System.out.println(campanhaGetResponse);
            System.out.println("---------------------------");
        }

    }

    @Test
    public void testCriaLead() throws Exception {

        LeadCampanhaGetResponseDTO lead = new LeadCampanhaGetResponseDTO();
        lead.setName("Eduardo teste api");
        lead.setEmail("eduardodevjavaweb@gmail.com");

        LeadCampanhaGetResponseCadastroDTO campanha = new LeadCampanhaGetResponseCadastroDTO();
        campanha.setCampaignId("******");
        lead.setCampaign(campanha);

        String retorno = serviceGetResponseEmailMarketing.criaLeadApiGetResponse(lead);

        System.out.println(retorno);

    }
}
