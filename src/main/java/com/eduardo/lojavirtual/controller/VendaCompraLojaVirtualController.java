package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.*;
import com.eduardo.lojavirtual.model.dto.ItemVendaDTO;
import com.eduardo.lojavirtual.model.dto.ProdutoDTO;
import com.eduardo.lojavirtual.model.dto.VendaCompraLojaVirtualDTO;
import com.eduardo.lojavirtual.repository.EnderecoRepository;
import com.eduardo.lojavirtual.repository.NotaFiscalVendaRepository;
import com.eduardo.lojavirtual.repository.StatusRastreioRepository;
import com.eduardo.lojavirtual.repository.VendaCompraLojaVirtualRepository;
import com.eduardo.lojavirtual.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VendaCompraLojaVirtualController {

    @Autowired
    private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaController pessoaController;

    @Autowired
    private NotaFiscalVendaRepository notaFiscalVendaRepository;

    @Autowired
    private StatusRastreioRepository statusRastreioRepository;

    @Autowired
    private VendaService vendaService;

    @ResponseBody
    @PostMapping(value = "**/salvarVendaLoja")
    public ResponseEntity<VendaCompraLojaVirtualDTO> salvarVendaLoja(@Valid @RequestBody VendaCompraLojaVirtual vendaCompraLojaVirtual) throws ExceptionMentoriaJava {

        vendaCompraLojaVirtual.getPessoa().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        PessoaFisica pessoaFisica = pessoaController.salvarPf(vendaCompraLojaVirtual.getPessoa()).getBody();
        vendaCompraLojaVirtual.setPessoa(pessoaFisica);

        vendaCompraLojaVirtual.getEnderecoCobranca().setPessoa(pessoaFisica);
        vendaCompraLojaVirtual.getEnderecoCobranca().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        Endereco enderecoCobranca = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoCobranca());
        vendaCompraLojaVirtual.setEnderecoCobranca(enderecoCobranca);

        vendaCompraLojaVirtual.getEnderecoEntrega().setPessoa(pessoaFisica);
        vendaCompraLojaVirtual.getEnderecoEntrega().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        Endereco enderecoEntrega = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoEntrega());
        vendaCompraLojaVirtual.setEnderecoEntrega(enderecoEntrega);

        vendaCompraLojaVirtual.getNotaFiscalVenda().setEmpresa(vendaCompraLojaVirtual.getEmpresa());

        for (int i = 0; i < vendaCompraLojaVirtual.getItemVendaLojas().size(); i++) {
            vendaCompraLojaVirtual.getItemVendaLojas().get(i).setEmpresa(vendaCompraLojaVirtual.getEmpresa());
            vendaCompraLojaVirtual.getItemVendaLojas().get(i).setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
        }

        /*Salva primeiro a venda e todos os dados*/
        vendaCompraLojaVirtual = vendaCompraLojaVirtualRepository.saveAndFlush(vendaCompraLojaVirtual);

        StatusRastreio statusRastreio = new StatusRastreio();
        statusRastreio.setCentroDistribuicao("CD Eduardo");
        statusRastreio.setCidade("Recife");
        statusRastreio.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        statusRastreio.setEstado("PE");
        statusRastreio.setStatus("Inicio Compra");
        statusRastreio.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);

        statusRastreioRepository.save(statusRastreio);

        /*Associa a venda gravada no banco com a nota fiscal*/
        vendaCompraLojaVirtual.getNotaFiscalVenda().setVendaCompraLojaVirtual(vendaCompraLojaVirtual);

        /*Persiste novamente as nota fiscal novamente pra ficar amarrada na venda*/
        notaFiscalVendaRepository.saveAndFlush(vendaCompraLojaVirtual.getNotaFiscalVenda());

        VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

        compraLojaVirtualDTO.setId(vendaCompraLojaVirtual.getId());
        compraLojaVirtualDTO.setValorTotal(vendaCompraLojaVirtual.getValorTotal());
        compraLojaVirtualDTO.setEntrega(vendaCompraLojaVirtual.getEnderecoEntrega());
        compraLojaVirtualDTO.setCobranca(vendaCompraLojaVirtual.getEnderecoCobranca());
        compraLojaVirtualDTO.setPessoa(vendaCompraLojaVirtual.getPessoa());
        compraLojaVirtualDTO.setValorDesc(vendaCompraLojaVirtual.getValorDesconto());
        compraLojaVirtualDTO.setValorFrete(vendaCompraLojaVirtual.getValorFrete());

        for (ItemVendaLoja item : vendaCompraLojaVirtual.getItemVendaLojas()) {

            ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
            itemVendaDTO.setQuantidade(item.getQuantidade());
            itemVendaDTO.setProdutoDTO(new ProdutoDTO(item.getProduto()));

            compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
        }

        return new ResponseEntity<VendaCompraLojaVirtualDTO>(compraLojaVirtualDTO, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaVendaId/{id}")
    public ResponseEntity<VendaCompraLojaVirtualDTO> consultaVendaId(@PathVariable("id") Long idVenda) {

        VendaCompraLojaVirtual compraLojaVirtual = vendaCompraLojaVirtualRepository.findByIdExclusao(idVenda);

        if (compraLojaVirtual == null){
            compraLojaVirtual = new VendaCompraLojaVirtual();
        }

        VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

        compraLojaVirtualDTO.setId(compraLojaVirtual.getId());
        compraLojaVirtualDTO.setValorTotal(compraLojaVirtual.getValorTotal());
        compraLojaVirtualDTO.setPessoa(compraLojaVirtual.getPessoa());
        compraLojaVirtualDTO.setEntrega(compraLojaVirtual.getEnderecoEntrega());
        compraLojaVirtualDTO.setCobranca(compraLojaVirtual.getEnderecoCobranca());
        compraLojaVirtualDTO.setValorDesc(compraLojaVirtual.getValorDesconto());
        compraLojaVirtualDTO.setValorFrete(compraLojaVirtual.getValorFrete());

        for (ItemVendaLoja item : compraLojaVirtual.getItemVendaLojas()) {

            ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
            itemVendaDTO.setQuantidade(item.getQuantidade());
            itemVendaDTO.setProdutoDTO(new ProdutoDTO(item.getProduto()));

            compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
        }

        return new ResponseEntity<VendaCompraLojaVirtualDTO>(compraLojaVirtualDTO, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteVendaTotalBanco/{idVenda}")
    public ResponseEntity<String> deleteVendaTotalBanco(@PathVariable(value = "idVenda") Long idVenda) {
        vendaService.exclusaoTotalVendaBanco(idVenda);
        return new ResponseEntity<String>("Venda excluida com sucesso.",HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteVendaTotalBanco2/{idVenda}")
    public ResponseEntity<String> deleteVendaTotalBanco2(@PathVariable(value = "idVenda") Long idVenda) {
        vendaService.exclusaoTotalVendaBanco2(idVenda);
        return new ResponseEntity<String>("Venda excluida logicamente com sucesso.",HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping(value = "**/ativaRegistroVendaBanco/{idVenda}")
    public ResponseEntity<String> ativaRegistroVendaBanco(@PathVariable(value = "idVenda") Long idVenda) {
        vendaService.ativaRegistroVendaBanco(idVenda);
        return new ResponseEntity<String>("Venda ativada com sucesso.",HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaVendaPorProdutoId/{id}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>> consultaVendaPorProduto(@PathVariable("id") Long idProd) {

        List<VendaCompraLojaVirtual> compraLojaVirtual = vendaCompraLojaVirtualRepository.vendaPorProduto(idProd);

        if (compraLojaVirtual == null) {
            compraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
        }

        List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();

        for (VendaCompraLojaVirtual vcl : compraLojaVirtual) {

            VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

            compraLojaVirtualDTO.setValorTotal(vcl.getValorTotal());
            compraLojaVirtualDTO.setPessoa(vcl.getPessoa());

            compraLojaVirtualDTO.setEntrega(vcl.getEnderecoEntrega());
            compraLojaVirtualDTO.setCobranca(vcl.getEnderecoCobranca());

            compraLojaVirtualDTO.setValorDesc(vcl.getValorDesconto());
            compraLojaVirtualDTO.setValorFrete(vcl.getValorFrete());
            compraLojaVirtualDTO.setId(vcl.getId());

            for (ItemVendaLoja item : vcl.getItemVendaLojas()) {

                ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
                itemVendaDTO.setQuantidade(item.getQuantidade());
                itemVendaDTO.setProdutoDTO(new ProdutoDTO(item.getProduto()));

                compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
            }

            compraLojaVirtualDTOList.add(compraLojaVirtualDTO);

        }

        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(compraLojaVirtualDTOList, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaVendaDinamica/{valor}/{tipoconsulta}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>> consultaVendaDinamica(@PathVariable("valor") String valor,
                          @PathVariable("tipoconsulta") String tipoconsulta) {

        List<VendaCompraLojaVirtual> compraLojaVirtual = null;

        if (tipoconsulta.equalsIgnoreCase("POR_ID_PROD")) {
            compraLojaVirtual =  vendaCompraLojaVirtualRepository.vendaPorProduto(Long.parseLong(valor));
        }else if (tipoconsulta.equalsIgnoreCase("POR_NOME_PROD")) {
            compraLojaVirtual = vendaCompraLojaVirtualRepository.vendaPorNomeProduto(valor.toUpperCase().trim());
        }
        else if (tipoconsulta.equalsIgnoreCase("POR_NOME_CLIENTE")) {
            compraLojaVirtual = vendaCompraLojaVirtualRepository.vendaPorNomeCliente(valor.toUpperCase().trim());
        }
        else if (tipoconsulta.equalsIgnoreCase("POR_ENDERECO_COBRANCA")) {
            compraLojaVirtual = vendaCompraLojaVirtualRepository.vendaPorEndereCobranca(valor.toUpperCase().trim());
        }
        else if (tipoconsulta.equalsIgnoreCase("POR_ENDERECO_ENTREGA")) {
            compraLojaVirtual = vendaCompraLojaVirtualRepository.vendaPorEnderecoEntrega(valor.toUpperCase().trim());
        }

        if (compraLojaVirtual == null) {
            compraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
        }

        List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();

        for (VendaCompraLojaVirtual vcl : compraLojaVirtual) {

            VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

            compraLojaVirtualDTO.setValorTotal(vcl.getValorTotal());
            compraLojaVirtualDTO.setPessoa(vcl.getPessoa());

            compraLojaVirtualDTO.setEntrega(vcl.getEnderecoEntrega());
            compraLojaVirtualDTO.setCobranca(vcl.getEnderecoCobranca());

            compraLojaVirtualDTO.setValorDesc(vcl.getValorDesconto());
            compraLojaVirtualDTO.setValorFrete(vcl.getValorFrete());
            compraLojaVirtualDTO.setId(vcl.getId());

            for (ItemVendaLoja item : vcl.getItemVendaLojas()) {

                ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
                itemVendaDTO.setQuantidade(item.getQuantidade());
                itemVendaDTO.setProdutoDTO(new ProdutoDTO(item.getProduto()));

                compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
            }

            compraLojaVirtualDTOList.add(compraLojaVirtualDTO);

        }

        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(compraLojaVirtualDTOList, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaVendaDinamicaFaixaData/{data1}/{data2}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>> consultaVendaDinamicaFaixaData(
                                                                @PathVariable("data1") String data1,
                                                                @PathVariable("data2") String data2) throws ParseException {

        List<VendaCompraLojaVirtual> compraLojaVirtual = null;

        compraLojaVirtual = vendaService.consultaVendaFaixaData(data1, data2);

        if (compraLojaVirtual == null) {
            compraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
        }

        List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();

        for (VendaCompraLojaVirtual vcl : compraLojaVirtual) {

            VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

            compraLojaVirtualDTO.setValorTotal(vcl.getValorTotal());
            compraLojaVirtualDTO.setPessoa(vcl.getPessoa());

            compraLojaVirtualDTO.setEntrega(vcl.getEnderecoEntrega());
            compraLojaVirtualDTO.setCobranca(vcl.getEnderecoCobranca());

            compraLojaVirtualDTO.setValorDesc(vcl.getValorDesconto());
            compraLojaVirtualDTO.setValorFrete(vcl.getValorFrete());
            compraLojaVirtualDTO.setId(vcl.getId());

            for (ItemVendaLoja item : vcl.getItemVendaLojas()) {

                ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
                itemVendaDTO.setQuantidade(item.getQuantidade());
                itemVendaDTO.setProdutoDTO(new ProdutoDTO(item.getProduto()));

                compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
            }

            compraLojaVirtualDTOList.add(compraLojaVirtualDTO);

        }

        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(compraLojaVirtualDTOList, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/vendaPorCliente/{idCliente}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>> vendaPorCliente(@PathVariable("idCliente") Long idCliente) {

        List<VendaCompraLojaVirtual> compraLojaVirtual = vendaCompraLojaVirtualRepository.vendaPorCliente(idCliente);

        if (compraLojaVirtual == null) {
            compraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
        }

        List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();

        for (VendaCompraLojaVirtual vcl : compraLojaVirtual) {

            VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

            compraLojaVirtualDTO.setValorTotal(vcl.getValorTotal());
            compraLojaVirtualDTO.setPessoa(vcl.getPessoa());

            compraLojaVirtualDTO.setEntrega(vcl.getEnderecoEntrega());
            compraLojaVirtualDTO.setCobranca(vcl.getEnderecoCobranca());

            compraLojaVirtualDTO.setValorDesc(vcl.getValorDesconto());
            compraLojaVirtualDTO.setValorFrete(vcl.getValorFrete());
            compraLojaVirtualDTO.setId(vcl.getId());

            for (ItemVendaLoja item : vcl.getItemVendaLojas()) {

                ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
                itemVendaDTO.setQuantidade(item.getQuantidade());
                itemVendaDTO.setProdutoDTO(new ProdutoDTO(item.getProduto()));

                compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
            }

            compraLojaVirtualDTOList.add(compraLojaVirtualDTO);

        }

        return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(compraLojaVirtualDTOList, HttpStatus.OK);
    }

}

