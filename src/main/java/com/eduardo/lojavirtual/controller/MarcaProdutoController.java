package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.MarcaProduto;
import com.eduardo.lojavirtual.repository.MarcaProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MarcaProdutoController {

    @Autowired
    private MarcaProdutoRepository marcaProdutoRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarMarca")
    public ResponseEntity<MarcaProduto> salvarMarca(@Valid @RequestBody MarcaProduto marcaProduto) throws ExceptionMentoriaJava {

        if (marcaProduto.getId() == null) {
            List<MarcaProduto> marcas = marcaProdutoRepository.buscarMarcaDesc(marcaProduto.getNomeDesc().toUpperCase());

            if (!marcas.isEmpty()) {
                throw new ExceptionMentoriaJava("Já existe Marca com a descrição: " + marcaProduto.getNomeDesc());
            }
        }

        MarcaProduto marcaProdutoSalvo = marcaProdutoRepository.save(marcaProduto);

        return new ResponseEntity<MarcaProduto>(marcaProdutoSalvo, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/deleteMarca")
    public ResponseEntity<?> deleteMarca(@RequestBody MarcaProduto marcaProduto) {
        marcaProdutoRepository.deleteById(marcaProduto.getId());
        return new ResponseEntity("Marca Removida",HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteMarcaPorId/{id}")
    public ResponseEntity<?> deleteMarcaPorId(@PathVariable("id") Long id) {
        marcaProdutoRepository.deleteById(id);
        return new ResponseEntity("Marca Removida",HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/obterMarca/{id}")
    public ResponseEntity<MarcaProduto> obterMarca(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        MarcaProduto marcaProduto = marcaProdutoRepository.findById(id).orElse(null);

        if (marcaProduto == null) {
            throw new ExceptionMentoriaJava("Não encontrou a Marca com código: " + id);
        }

        return new ResponseEntity<MarcaProduto>(marcaProduto, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarMarcaPorDesc/{desc}")
    public ResponseEntity<List<MarcaProduto>> buscarMarcaPorDesc(@PathVariable("desc") String desc) {
        List<MarcaProduto> marcaProduto = marcaProdutoRepository.buscarMarcaDesc(desc.toUpperCase());
        return new ResponseEntity<List<MarcaProduto>>(marcaProduto,HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPorDescEEmpresaMarca/{desc}/{empresa}")
    public ResponseEntity<List<MarcaProduto>> buscarPorDescEEmpresa(@PathVariable("desc") String desc, @PathVariable("empresa") Long empresa) {

        List<MarcaProduto> marcaProduto = marcaProdutoRepository.buscarMarcaPorDescEEmpresa(desc.toUpperCase(), empresa);

        return new ResponseEntity<List<MarcaProduto>>(marcaProduto, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/qtdPaginaMarcaProduto/{idEmpresa}")
    public ResponseEntity<Integer> qtdPagina(@PathVariable Long idEmpresa){
        Integer qtdPagina = marcaProdutoRepository.qdtPagina(idEmpresa);
        return new ResponseEntity<Integer>(qtdPagina, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listaPorPageMarcaProduto/{idEmpresa}/{pagina}")
    public ResponseEntity<List<MarcaProduto>> pages(@PathVariable Long idEmpresa,
                                                        @PathVariable Integer pagina){
        Pageable pages = PageRequest.of(pagina, 5, Sort.by("nomeDesc"));
        List<MarcaProduto> lista = marcaProdutoRepository.findPorPage(idEmpresa, pages);
        return new ResponseEntity<List<MarcaProduto>>(lista, HttpStatus.OK);
    }
}
