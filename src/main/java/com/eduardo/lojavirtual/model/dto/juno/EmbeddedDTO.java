package com.eduardo.lojavirtual.model.dto.juno;

import java.util.ArrayList;
import java.util.List;

public class EmbeddedDTO {

    private List<ConteudoBoletoJunoDTO> charges = new ArrayList<ConteudoBoletoJunoDTO>();

    public void setCharges(List<ConteudoBoletoJunoDTO> charges) {
        this.charges = charges;
    }

    public List<ConteudoBoletoJunoDTO> getCharges() {
        return charges;
    }
}
