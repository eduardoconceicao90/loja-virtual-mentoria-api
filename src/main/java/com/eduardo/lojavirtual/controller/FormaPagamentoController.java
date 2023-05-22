package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.FormaPagamento;
import com.eduardo.lojavirtual.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarFormaPagamento")
    public ResponseEntity<FormaPagamento> salvarFormaPagamento(@Valid @RequestBody FormaPagamento formaPagamento)
            throws ExceptionMentoriaJava {
        formaPagamento = formaPagamentoRepository.save(formaPagamento);
        return new ResponseEntity<FormaPagamento>(formaPagamento, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listaFormaPagamento/{idEmpresa}")
    public ResponseEntity<List<FormaPagamento>> listaFormaPagamentoidEmpresa(@PathVariable(value = "idEmpresa") Long idEmpresa){
        return new ResponseEntity<List<FormaPagamento>>(formaPagamentoRepository.findAll(idEmpresa), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listaFormaPagamento")
    public ResponseEntity<List<FormaPagamento>> listaFormaPagamento(){
        return new ResponseEntity<List<FormaPagamento>>(formaPagamentoRepository.findAll(), HttpStatus.OK);
    }
}
