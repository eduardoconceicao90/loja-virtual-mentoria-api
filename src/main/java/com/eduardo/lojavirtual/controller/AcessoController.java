package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.Acesso;
import com.eduardo.lojavirtual.repository.AcessoRepository;
import com.eduardo.lojavirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @ResponseBody /* Poder dar um retorno da API */
    @PostMapping(value = "**/salvarAcesso") /* Mapeando a url para receber JSON */
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionMentoriaJava { /* Recebe o JSON e converte para Objeto*/

        if(acesso.getId() == null) {
            List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
            if(!acessos.isEmpty()){
                throw new ExceptionMentoriaJava("Já existe acesso com a descrição: " + acesso.getDescricao());
            }
        }

        Acesso acessoSalvo = acessoService.save(acesso);
        return ResponseEntity.ok().body(acessoSalvo);
    }

    @ResponseBody
    @PostMapping(value = "**/deletarAcesso")
    public ResponseEntity<?> delete(@RequestBody Acesso acesso){
        acessoService.delete(acesso.getId());
        return new ResponseEntity("Acesso removido", HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deletarAcessoPorId/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        acessoService.delete(id);
        return new ResponseEntity("Acesso removido", HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @GetMapping(value = "**/obterAcesso/{id}")
    public ResponseEntity<Acesso> obterAcesso(@PathVariable Long id) throws ExceptionMentoriaJava {
        Acesso acesso = acessoRepository.findById(id).orElse(null);
        if (acesso == null){
            throw new ExceptionMentoriaJava("Não encontrou Acesso com o codigo: " + id);
        }
        return new ResponseEntity(acesso, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPorDesc/{desc}")
    public ResponseEntity<List<Acesso>> buscarPorDesc(@PathVariable String desc){
        List<Acesso> acesso = acessoRepository.buscarAcessoDesc(desc.toUpperCase());
        return new ResponseEntity(acesso, HttpStatus.OK);
    }
}
