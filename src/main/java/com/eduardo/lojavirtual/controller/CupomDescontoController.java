package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.CupomDesconto;
import com.eduardo.lojavirtual.model.MarcaProduto;
import com.eduardo.lojavirtual.repository.CupomDescontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CupomDescontoController {

    @Autowired
    private CupomDescontoRepository cupomDescontoRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarCupomDesc")
    public ResponseEntity<CupomDesconto> salvarCupomDesc(@Valid @RequestBody CupomDesconto cupomDesconto) {
        CupomDesconto cupomDescontoSalvo = cupomDescontoRepository.save(cupomDesconto);
        return new ResponseEntity<CupomDesconto>(cupomDescontoSalvo, HttpStatus.OK);
    }

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

    @ResponseBody
    @DeleteMapping(value = "**/deleteCupomDescPorId/{id}")
    public ResponseEntity<?> deleteCupomDescPorId(@PathVariable("id") Long id) {
        cupomDescontoRepository.deleteById(id);
        return new ResponseEntity("Cupom Produto Removido",HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/obterCupomDesconto/{id}")
    public ResponseEntity<CupomDesconto> obterCupomDesconto(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        CupomDesconto cupomDesconto = cupomDescontoRepository.findById(id).orElse(null);

        if (cupomDesconto == null) {
            throw new ExceptionMentoriaJava("Não encontrou Cupom Produto com código: " + id);
        }

        return new ResponseEntity<CupomDesconto>(cupomDesconto, HttpStatus.OK);
    }


}
