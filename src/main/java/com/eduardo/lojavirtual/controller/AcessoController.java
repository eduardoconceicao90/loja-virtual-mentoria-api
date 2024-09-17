package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.Acesso;
import com.eduardo.lojavirtual.repository.AcessoRepository;
import com.eduardo.lojavirtual.service.AcessoService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<String> delete(@RequestBody Acesso acesso){
        acessoService.delete(acesso.getId());
        return new ResponseEntity<String>(new Gson().toJson("Acesso removido"), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deletarAcessoPorId/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        acessoService.delete(id);
        return new ResponseEntity<String>(new Gson().toJson("Acesso removido"), HttpStatus.OK);
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

    @ResponseBody
    @GetMapping(value = "**/buscarPorDescEEmpresaAcesso/{desc}/{empresa}")
    public ResponseEntity<List<Acesso>> buscarPorDescEEmpresa(@PathVariable("desc") String desc, @PathVariable("empresa") Long empresa) {
        List<Acesso> acesso = acessoRepository.buscarAcessoPorDescEEmpresa(desc.toUpperCase(), empresa);
        return new ResponseEntity<List<Acesso>>(acesso, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/qtdPaginaAcesso/{idEmpresa}")
    public ResponseEntity<Integer> qtdPagina(@PathVariable Long idEmpresa){
        Integer qtdPagina = acessoRepository.qdtPagina(idEmpresa);
        return new ResponseEntity<Integer>(qtdPagina, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listaPorPageAcesso/{idEmpresa}/{pagina}")
    public ResponseEntity<List<Acesso>> pages(@PathVariable Long idEmpresa,
                                              @PathVariable Integer pagina){
        Pageable pages = PageRequest.of(pagina, 5, Sort.by("descricao"));
        List<Acesso> lista = acessoRepository.findPorPage(idEmpresa, pages);
        return new ResponseEntity<List<Acesso>>(lista, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listarAcesso/{idEmpresa}")
    public ResponseEntity<List<Acesso>> listarAcesso(@PathVariable Long idEmpresa){
        List<Acesso> lista = acessoRepository.listarAcesso(idEmpresa);
        return new ResponseEntity<List<Acesso>>(lista, HttpStatus.OK);
    }
}
