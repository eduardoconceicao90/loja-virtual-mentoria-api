package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.model.Acesso;
import com.eduardo.lojavirtual.repository.AcessoRepository;
import com.eduardo.lojavirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/acesso")
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @PostMapping(value = "/salvarAcesso") /* Mapeando a url para receber JSON */
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso){ /* Recebe o JSON e converte para Objeto*/
        Acesso acessoSalvo = acessoService.save(acesso);
        return ResponseEntity.ok().body(acessoSalvo);
    }

    @ResponseBody
    @PostMapping(value = "/deletarAcesso")
    public ResponseEntity<Void> delete(@RequestBody Acesso acesso){
        acessoService.delete(acesso.getId());
        return new ResponseEntity("Acesso removido", HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @DeleteMapping(value = "/deletarAcessoPorId/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        acessoService.delete(id);
        return new ResponseEntity("Acesso removido", HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @GetMapping(value = "/obterAcesso/{id}")
    public ResponseEntity<Acesso> obterAcesso(@PathVariable Long id){
        Acesso acesso = acessoRepository.findById(id).get();
        return new ResponseEntity(acesso, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/buscarPorDesc/{desc}")
    public ResponseEntity<List<Acesso>> obterAcesso(@PathVariable String desc){
        List<Acesso> acesso = acessoRepository.buscarAcessoDesc(desc);
        return new ResponseEntity(acesso, HttpStatus.OK);
    }
}
