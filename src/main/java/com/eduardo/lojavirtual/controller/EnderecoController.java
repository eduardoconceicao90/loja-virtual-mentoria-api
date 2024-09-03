package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.model.Endereco;
import com.eduardo.lojavirtual.repository.EnderecoRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @ResponseBody
    @PostMapping(value = "**/deletarEndereco")
    public ResponseEntity<String> delete(@RequestBody Endereco endereco){
        enderecoRepository.deleteById(endereco.getId());
        return new ResponseEntity<String>(new Gson().toJson("Endereço removido"), HttpStatus.OK);
    }
}
