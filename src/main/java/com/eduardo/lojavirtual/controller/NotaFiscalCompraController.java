package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.NotaFiscalCompra;
import com.eduardo.lojavirtual.repository.NotaFiscalCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class NotaFiscalCompraController {

    @Autowired
    private NotaFiscalCompraRepository notaFiscalCompraRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarNotaFiscalCompra")
    public ResponseEntity<NotaFiscalCompra> salvarNotaFiscalCompra(@Valid @RequestBody NotaFiscalCompra notaFiscalCompra) throws ExceptionMentoriaJava { /*Recebe o JSON e converte pra Objeto*/

        if (notaFiscalCompra.getId() == null) {

            if (notaFiscalCompra.getDescricaoObs() != null) {
                boolean existe = notaFiscalCompraRepository.existeNotaComDescricao(notaFiscalCompra.getDescricaoObs().toUpperCase().trim());

                if(existe) {
                    throw new ExceptionMentoriaJava("Já existe Nota de compra com essa mesma descrição: " + notaFiscalCompra.getDescricaoObs());
                }
            }

        }

        if (notaFiscalCompra.getPessoa() == null || notaFiscalCompra.getPessoa().getId() <= 0) {
            throw new ExceptionMentoriaJava("A Pessoa Juridica da nota fiscal deve ser informada.");
        }

        if (notaFiscalCompra.getEmpresa() == null || notaFiscalCompra.getEmpresa().getId() <= 0) {
            throw new ExceptionMentoriaJava("A empresa responsável deve ser infromada.");
        }

        if (notaFiscalCompra.getContaPagar() == null || notaFiscalCompra.getContaPagar().getId() <= 0) {
            throw new ExceptionMentoriaJava("A conta a pagar da nota deve ser informada.");
        }

        NotaFiscalCompra notaFiscalCompraSalva = notaFiscalCompraRepository.save(notaFiscalCompra);

        return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompraSalva, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteNotaFiscalCompraPorId/{id}")
    public ResponseEntity<?> deleteNotaFiscalCompraPorId(@PathVariable("id") Long id) {

        notaFiscalCompraRepository.deleteItemNotaFiscalCompra(id);/* Delete os filhos */
        notaFiscalCompraRepository.deleteById(id); /* Deleta o pai */

        return new ResponseEntity("Nota Fiscal Compra Removida",HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/obterNotaFiscalCompra/{id}")
    public ResponseEntity<NotaFiscalCompra> obterNotaFiscalCompra(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(id).orElse(null);

        if (notaFiscalCompra == null) {
            throw new ExceptionMentoriaJava("Não encontrou Nota Fiscal com código: " + id);
        }

        return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompra, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarNotaFiscalPorDesc/{desc}")
    public ResponseEntity<List<NotaFiscalCompra>> buscarNotaFiscalPorDesc(@PathVariable("desc") String desc) {
        List<NotaFiscalCompra>  notaFiscalCompra = notaFiscalCompraRepository.buscaNotaDesc(desc.toUpperCase().trim());
        return new ResponseEntity<List<NotaFiscalCompra>>(notaFiscalCompra, HttpStatus.OK);
    }

}
