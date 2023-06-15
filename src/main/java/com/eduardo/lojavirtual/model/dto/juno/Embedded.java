package com.eduardo.lojavirtual.model.dto.juno;

import java.util.ArrayList;
import java.util.List;

public class Embedded {

    private List<ConteudoBoletoJuno> charges = new ArrayList<ConteudoBoletoJuno>();

    public void setCharges(List<ConteudoBoletoJuno> charges) {
        this.charges = charges;
    }

    public List<ConteudoBoletoJuno> getCharges() {
        return charges;
    }
}
