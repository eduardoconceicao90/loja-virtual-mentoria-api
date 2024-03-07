package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.CategoriaProduto;
import com.eduardo.lojavirtual.model.dto.CatgoriaProdutoDTO;
import com.eduardo.lojavirtual.repository.CategoriaProdutoRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @ResponseBody
    @GetMapping(value = "**/buscarCategoriaPorId/{id}")
    public ResponseEntity<CategoriaProduto> buscarCategoriaPorId(@PathVariable("id") Long id) {

       CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(id).get();

        return new ResponseEntity<CategoriaProduto>(categoriaProduto, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPorDescCategoria/{desc}")
    public ResponseEntity<List<CategoriaProduto>> buscarPorDesc(@PathVariable("desc") String desc) {

        List<CategoriaProduto> categoriaProduto = categoriaProdutoRepository.buscarCategoriaDes(desc.toUpperCase());

        return new ResponseEntity<List<CategoriaProduto>>(categoriaProduto, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPorDescEEmpresaCategoria/{desc}/{empresa}")
    public ResponseEntity<List<CategoriaProduto>> buscarPorDescEEmpresa(@PathVariable("desc") String desc, @PathVariable("empresa") Long empresa) {

        List<CategoriaProduto> categoriaProduto = categoriaProdutoRepository.buscarCategoriaPorDescEEmpresa(desc.toUpperCase(), empresa);

        return new ResponseEntity<List<CategoriaProduto>>(categoriaProduto, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listarCategoriaProduto/{codEmpresa}")
    public ResponseEntity<List<CategoriaProduto>> listarCategoriaProduto(@PathVariable("codEmpresa") Long codEmpresa) {

        List<CategoriaProduto> categoriaProduto = categoriaProdutoRepository.findAll(codEmpresa);

        return new ResponseEntity<List<CategoriaProduto>>(categoriaProduto, HttpStatus.OK);
    }

    @ResponseBody /* Poder dar um retorno da API */
    @PostMapping(value = "**/deleteCategoria") /* Mapeando a url para receber JSON */
    public ResponseEntity<String> deleteCategoria(@RequestBody CategoriaProduto categoriaProduto) throws ExceptionMentoriaJava { /* Recebe o JSON e converte pra Objeto */

        if (categoriaProdutoRepository.findById(categoriaProduto.getId()).isPresent() == false) {
            throw new ExceptionMentoriaJava("Categoria já foi removida");
        }

        categoriaProdutoRepository.deleteById(categoriaProduto.getId());

        return new ResponseEntity<String>(new Gson().toJson("Categoria removida"),HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/salvarCategoria")
    public ResponseEntity<CatgoriaProdutoDTO> salvarCategoria(@RequestBody CategoriaProduto categoriaProduto) throws ExceptionMentoriaJava {

        if (categoriaProduto.getEmpresa() == null || (categoriaProduto.getEmpresa().getId() == null)) {
            throw new ExceptionMentoriaJava("A empresa deve ser informada.");
        }

        if (categoriaProduto.getId() == null && categoriaProdutoRepository.existeCategoria(categoriaProduto.getNomeDesc())) {
            throw new ExceptionMentoriaJava("Não pode cadastrar categoria com mesmo nome.");
        }

        CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);

        CatgoriaProdutoDTO catgoriaProdutoDto = new CatgoriaProdutoDTO();
        catgoriaProdutoDto.setId(categoriaSalva.getId());
        catgoriaProdutoDto.setNomeDesc(categoriaSalva.getNomeDesc());
        catgoriaProdutoDto.setEmpresa(categoriaSalva.getEmpresa().getId().toString());

        return new ResponseEntity<CatgoriaProdutoDTO>(catgoriaProdutoDto, HttpStatus.OK);
    }

}
