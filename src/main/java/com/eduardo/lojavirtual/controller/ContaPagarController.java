package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.ContaPagar;
import com.eduardo.lojavirtual.repository.ContaPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContaPagarController {

    @Autowired
    private ContaPagarRepository contaPagarRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarContaPagar")
    public ResponseEntity<ContaPagar> salvarContaPagar(@Valid @RequestBody ContaPagar contaPagar) throws ExceptionMentoriaJava {

        if (contaPagar.getId() == null) {
            List<ContaPagar> contasPagar = contaPagarRepository.buscarContaDesc(contaPagar.getDescricao().toUpperCase().trim());

            if (!contasPagar.isEmpty()) {
                throw new ExceptionMentoriaJava("Já existe Conta a Pagar com a descrição: " + contaPagar.getDescricao());
            }

            if (contaPagar.getPessoaFisica() == null || contaPagar.getPessoaFisica().getId() <= 0) {
                throw new ExceptionMentoriaJava("A pessoa responsável pela conta deve ser informada");
            }

            if (contaPagar.getEmpresa() == null || contaPagar.getEmpresa().getId() <= 0) {
                throw new ExceptionMentoriaJava("A Empresa responsável pela conta deve ser informada");
            }
        }

        ContaPagar contaPagarSalva = contaPagarRepository.save(contaPagar);

        return new ResponseEntity<ContaPagar>(contaPagarSalva, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/deleteContaPagar")
    public ResponseEntity<?> deleteContaPagar(@RequestBody ContaPagar contaPagar) {
        contaPagarRepository.deleteById(contaPagar.getId());
        return new ResponseEntity("Conta Pagar Removida",HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteContaPagarPorId/{id}")
    public ResponseEntity<?> deleteContaPagarPorId(@PathVariable("id") Long id) {
        contaPagarRepository.deleteById(id);
        return new ResponseEntity("Conta Pagar Removida",HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/obterContaPagar/{id}")
    public ResponseEntity<ContaPagar> obterContaPagar(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        ContaPagar contaPagar = contaPagarRepository.findById(id).orElse(null);

        if (contaPagar == null) {
            throw new ExceptionMentoriaJava("Não encontrou Conta Pagar com código: " + id);
        }

        return new ResponseEntity<ContaPagar>(contaPagar,HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPorContaDesc/{desc}")
    public ResponseEntity<List<ContaPagar>> buscarPorContaDesc(@PathVariable("desc") String desc) {
        List<ContaPagar> contaPagar = contaPagarRepository.buscarContaDesc(desc.toUpperCase());
        return new ResponseEntity<List<ContaPagar>>(contaPagar, HttpStatus.OK);
    }

}