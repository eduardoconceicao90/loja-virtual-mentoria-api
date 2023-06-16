package com.eduardo.lojavirtual.model.dto.juno;

import java.util.ArrayList;
import java.util.List;

public class BoletoGeradoApiJunoDTO {

    private EmbeddedDTO _embedded = new EmbeddedDTO();
    private List<LinksDTO> _links = new ArrayList<LinksDTO>();

    public void set_embedded(EmbeddedDTO _embedded) {
        this._embedded = _embedded;
    }

    public EmbeddedDTO get_embedded() {
        return _embedded;
    }

    public void set_links(List<LinksDTO> _links) {
        this._links = _links;
    }

    public List<LinksDTO> get_links() {
        return _links;
    }
}
