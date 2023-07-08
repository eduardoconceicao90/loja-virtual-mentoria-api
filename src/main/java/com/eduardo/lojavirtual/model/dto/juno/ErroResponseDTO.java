package com.eduardo.lojavirtual.model.dto.juno;

import java.util.ArrayList;
import java.util.List;

public class ErroResponseDTO {

    private String timestamp;
    private String status;
    private String error;
    private String path;
    private List<DetailsErroDTO> details = new ArrayList<DetailsErroDTO>();

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<DetailsErroDTO> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsErroDTO> details) {
        this.details = details;
    }

    public String listaErro() {
        String retorno = "";

        for (DetailsErroDTO detailsErro : details) {
            retorno += detailsErro.getMessage() + "\n";
        }

        return retorno;
    }
}
