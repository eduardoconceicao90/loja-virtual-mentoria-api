package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.AvaliacaoProduto;
import com.eduardo.lojavirtual.model.dto.AvaliacaoProdutoDTO;
import com.eduardo.lojavirtual.repository.AvaliacaoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AvaliacaoProdutoController {

    @Autowired
    private AvaliacaoProdutoRepository avaliacaoProdutoRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarAvaliacaoProduto")
    public ResponseEntity<AvaliacaoProduto> salvarAvaliacaoProduto(@Valid @RequestBody AvaliacaoProduto avaliacaoProduto) throws ExceptionMentoriaJava {

        if (avaliacaoProduto.getEmpresa() == null || (avaliacaoProduto.getEmpresa() != null && avaliacaoProduto.getEmpresa().getId() <= 0)) {
            throw new ExceptionMentoriaJava("Informe a empresa dona do registro");
        }

        if(avaliacaoProduto.getProduto() == null || (avaliacaoProduto.getProduto() != null && avaliacaoProduto.getProduto().getId() <= 0)) {
            throw new ExceptionMentoriaJava("A avaliação deve conter o produto associado.");
        }

        if(avaliacaoProduto.getPessoa() == null || (avaliacaoProduto.getPessoa() != null && avaliacaoProduto.getPessoa().getId() <= 0)) {
            throw new ExceptionMentoriaJava("A avaliação deve conter a pessoa ou cliente associado.");
        }

        avaliacaoProduto = avaliacaoProdutoRepository.saveAndFlush(avaliacaoProduto);
        return new ResponseEntity<AvaliacaoProduto>(avaliacaoProduto, HttpStatus.OK);

    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteAvalicaoPessoa/{idAvaliacao}")
    public ResponseEntity<?> deleteAvalicaoPessoa(@PathVariable("idAvaliacao") Long idAvaliacao) {
        avaliacaoProdutoRepository.deleteById(idAvaliacao);
        return new ResponseEntity("Avaliacao Removida", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/avaliacaoProduto/{idProduto}")
    public ResponseEntity<List<AvaliacaoProdutoDTO>> avaliacaoProduto(@PathVariable("idProduto") Long idProduto) {
        List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.avaliacaoProduto(idProduto);
        List<AvaliacaoProdutoDTO> avaliacaoProdutosList = avaliacaoProdutos.stream().map(obj -> new AvaliacaoProdutoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(avaliacaoProdutosList);
    }

    @ResponseBody
    @GetMapping(value = "**/avaliacaoPessoa/{idPessoa}")
    public ResponseEntity<List<AvaliacaoProdutoDTO>> avaliacaoPessoa(@PathVariable("idPessoa") Long idPessoa) {
        List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.avaliacaoPessoa(idPessoa);
        List<AvaliacaoProdutoDTO> avaliacaoProdutosList = avaliacaoProdutos.stream().map(obj -> new AvaliacaoProdutoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(avaliacaoProdutosList);
    }

    @ResponseBody
    @GetMapping(value = "**/avaliacaoProdutoPessoa/{idProduto}/{idPessoa}")
    public ResponseEntity<List<AvaliacaoProdutoDTO>> avaliacaoProdutoPessoa(@PathVariable("idProduto") Long idProduto, @PathVariable("idPessoa") Long idPessoa) {
        List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.avaliacaoProdutoPessoa(idProduto, idPessoa);
        List<AvaliacaoProdutoDTO> avaliacaoProdutosList = avaliacaoProdutos.stream().map(obj -> new AvaliacaoProdutoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(avaliacaoProdutosList);
    }

}
