package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.model.Acesso;
import com.eduardo.lojavirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/acesso")
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/salvarAcesso") /* Mapeando a url para receber JSON */
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso){ /* Recebe o JSON e converte para Objeto*/
        Acesso acessoSalvo = acessoService.save(acesso);
        return ResponseEntity.ok().body(acessoSalvo);
    }
}
