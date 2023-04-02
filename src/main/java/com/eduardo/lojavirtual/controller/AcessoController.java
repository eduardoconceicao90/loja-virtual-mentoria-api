package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.model.Acesso;
import com.eduardo.lojavirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/acesso")
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @PostMapping
    public Acesso salvarAcesso(Acesso acesso){
        return acessoService.save(acesso);
    }
}
