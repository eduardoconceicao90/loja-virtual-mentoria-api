package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.NotaFiscalCompra;
import com.eduardo.lojavirtual.model.NotaFiscalVenda;
import com.eduardo.lojavirtual.model.dto.ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO;
import com.eduardo.lojavirtual.model.dto.ObejtoRequisicaoRelatorioProdutoAlertaEstoqueDTO;
import com.eduardo.lojavirtual.model.dto.ObjetoRelatorioStatusCompraDTO;
import com.eduardo.lojavirtual.repository.NotaFiscalCompraRepository;
import com.eduardo.lojavirtual.repository.NotaFiscalVendaRepository;
import com.eduardo.lojavirtual.service.NotaFiscalCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NotaFiscalCompraController {

    @Autowired
    private NotaFiscalCompraRepository notaFiscalCompraRepository;

    @Autowired
    private NotaFiscalVendaRepository notaFiscalVendaRepository;

    @Autowired
    private NotaFiscalCompraService notaFiscalCompraService;

    @ResponseBody
    @PostMapping(value = "**/relatorioStatusCompra")
    public ResponseEntity<List<ObjetoRelatorioStatusCompraDTO>> relatorioStatusCompra
            (@Valid @RequestBody ObjetoRelatorioStatusCompraDTO objetoRelatorioStatusCompraDto){

        List<ObjetoRelatorioStatusCompraDTO> retorno = new ArrayList<>();

        retorno = notaFiscalCompraService.relatorioStatusVendaLojaVirtual(objetoRelatorioStatusCompraDto);

        return new ResponseEntity<List<ObjetoRelatorioStatusCompraDTO>>(retorno, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/relatorioProdCompradoNotaFiscal")
    public ResponseEntity<List<ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO>> relatorioProdCompradoNotaFiscal
            (@Valid @RequestBody ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO objetoRequisicaoRelatorioProdCompraNotaFiscalDto){

        List<ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO> retorno = new ArrayList<>();

        retorno = notaFiscalCompraService.gerarRelatorioProdCompraNota(objetoRequisicaoRelatorioProdCompraNotaFiscalDto);

        return new ResponseEntity<List<ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO>>(retorno, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/relatorioProdAlertaEstoque")
    public ResponseEntity<List<ObejtoRequisicaoRelatorioProdutoAlertaEstoqueDTO>> relatorioProdAlertaEstoque
            (@Valid @RequestBody ObejtoRequisicaoRelatorioProdutoAlertaEstoqueDTO objetoRequisicaoRelatorioProdAlertaEstoqueDto){

        List<ObejtoRequisicaoRelatorioProdutoAlertaEstoqueDTO> retorno = new ArrayList<>();

        retorno = notaFiscalCompraService.gerarRelatorioAlertaEstoque(objetoRequisicaoRelatorioProdAlertaEstoqueDto);

        return new ResponseEntity<List<ObejtoRequisicaoRelatorioProdutoAlertaEstoqueDTO>>(retorno, HttpStatus.OK);
    }

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

    @ResponseBody
    @GetMapping(value = "**/obterNotaFiscalCompraDaVenda/{idvenda}")
    public ResponseEntity<List<NotaFiscalVenda>> obterNotaFiscalCompraDaVenda(@PathVariable("idvenda") Long idvenda) throws ExceptionMentoriaJava {

        List<NotaFiscalVenda> notaFiscalCompra = notaFiscalVendaRepository.buscaNotaPorVenda(idvenda);

        if (notaFiscalCompra == null) {
            throw new ExceptionMentoriaJava("Não encontrou Nota Fiscal de venda com código da venda: " + idvenda);
        }

        return new ResponseEntity<List<NotaFiscalVenda>>(notaFiscalCompra, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/obterNotaFiscalCompraDaVendaUnico/{idvenda}")
    public ResponseEntity<NotaFiscalVenda> obterNotaFiscalCompraDaVendaUnico(@PathVariable("idvenda") Long idvenda) throws ExceptionMentoriaJava {

        NotaFiscalVenda notaFiscalCompra = notaFiscalVendaRepository.buscaNotaPorVendaUnica(idvenda);

        if (notaFiscalCompra == null) {
            throw new ExceptionMentoriaJava("Não encontrou Nota Fiscal de venda com código da venda: " + idvenda);
        }

        return new ResponseEntity<NotaFiscalVenda>(notaFiscalCompra, HttpStatus.OK);
    }

}
