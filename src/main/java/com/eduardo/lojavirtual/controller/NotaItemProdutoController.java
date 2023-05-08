package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.NotaItemProduto;
import com.eduardo.lojavirtual.repository.NotaItemProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class NotaItemProdutoController {

    @Autowired
    private NotaItemProdutoRepository notaItemProdutoRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarNotaItemProduto")
    public ResponseEntity<NotaItemProduto> salvarNotaItemProduto(@Valid @RequestBody NotaItemProduto notaItemProduto) throws ExceptionMentoriaJava {

        if (notaItemProduto.getId() == null) {

            if (notaItemProduto.getProduto() == null || notaItemProduto.getProduto().getId() <= 0) {
                throw new ExceptionMentoriaJava("O produto deve ser informado.");
            }

            if (notaItemProduto.getNotaFiscalCompra() == null || notaItemProduto.getNotaFiscalCompra().getId() <= 0) {
                throw new ExceptionMentoriaJava("A nota fiscal deve ser informada.");
            }

            if (notaItemProduto.getEmpresa() == null || notaItemProduto.getEmpresa().getId() <= 0) {
                throw new ExceptionMentoriaJava("A empresa deve ser informada.");
            }

            List<NotaItemProduto> notaExistente = notaItemProdutoRepository.
                    buscaNotaItemPorProdutoNota(notaItemProduto.getProduto().getId(),
                            notaItemProduto.getNotaFiscalCompra().getId());

            if (!notaExistente.isEmpty()) {
                throw new ExceptionMentoriaJava("Já existe este produto cadastrado para esta nota.");
            }

        }

        if (notaItemProduto.getQuantidade() <=0) {
            throw new ExceptionMentoriaJava("A quantidade do produto deve ser informada.");
        }

        NotaItemProduto notaItemSalva = notaItemProdutoRepository.save(notaItemProduto);
        notaItemSalva = notaItemProdutoRepository.findById(notaItemProduto.getId()).get();
        return new ResponseEntity<NotaItemProduto>(notaItemSalva, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteNotaItemPorId/{id}")
    public ResponseEntity<?> deleteNotaItemPorId(@PathVariable("id") Long id) {
        notaItemProdutoRepository.deleteByIdNotaItem(id);
        return new ResponseEntity("Nota Item Produto Removido", HttpStatus.OK);
    }

}
