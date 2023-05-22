package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.model.CupomDesconto;
import com.eduardo.lojavirtual.repository.CupomDescontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CupomDescontoController {

    @Autowired
    private CupomDescontoRepository cupomDescontoRepository;

    @ResponseBody
    @GetMapping(value = "**/listaCupomDesc/{idEmpresa}")
    public ResponseEntity<List<CupomDesconto>> listaCupomDesc(@PathVariable("idEmpresa") Long idEmpresa){
        return new ResponseEntity<List<CupomDesconto>>(cupomDescontoRepository.cupomDescontoPorEmpresa(idEmpresa), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listaCupomDesc")
    public ResponseEntity<List<CupomDesconto>> listaCupomDesc(){
        return new ResponseEntity<List<CupomDesconto>>(cupomDescontoRepository.findAll(), HttpStatus.OK);
    }

}
