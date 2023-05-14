package com.eduardo.lojavirtual.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImagemProdutoDTO {

    private Long id;
    private String imagemOriginal;
    private String imagemMiniatura;
    private Long produto;
    private Long empresa;

}
