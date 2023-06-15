package com.eduardo.lojavirtual.model.dto.juno;

import java.util.ArrayList;
import java.util.List;

public class BoletoGeradoApiJuno {

    private Embedded _embedded = new Embedded();
    private List<Links> _links = new ArrayList<Links>();

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }

    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_links(List<Links> _links) {
        this._links = _links;
    }

    public List<Links> get_links() {
        return _links;
    }
}
