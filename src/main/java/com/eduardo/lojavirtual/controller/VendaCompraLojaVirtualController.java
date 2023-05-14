package com.eduardo.lojavirtual.controller;

import com.eduardo.lojavirtual.exception.ExceptionMentoriaJava;
import com.eduardo.lojavirtual.model.Endereco;
import com.eduardo.lojavirtual.model.ItemVendaLoja;
import com.eduardo.lojavirtual.model.PessoaFisica;
import com.eduardo.lojavirtual.model.VendaCompraLojaVirtual;
import com.eduardo.lojavirtual.model.dto.ItemVendaDTO;
import com.eduardo.lojavirtual.model.dto.VendaCompraLojaVirtualDTO;
import com.eduardo.lojavirtual.repository.EnderecoRepository;
import com.eduardo.lojavirtual.repository.NotaFiscalVendaRepository;
import com.eduardo.lojavirtual.repository.VendaCompraLojaVirtualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
            itemVendaDTO.setProduto(item.getProduto());

            compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
        }

        return new ResponseEntity<VendaCompraLojaVirtualDTO>(compraLojaVirtualDTO, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaVendaId/{id}")
    public ResponseEntity<VendaCompraLojaVirtualDTO> consultaVendaId(@PathVariable("id") Long idVenda) {

        VendaCompraLojaVirtual compraLojaVirtual = vendaCompraLojaVirtualRepository.findById(idVenda).orElse(new VendaCompraLojaVirtual());

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
            itemVendaDTO.setProduto(item.getProduto());

            compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
        }

        return new ResponseEntity<VendaCompraLojaVirtualDTO>(compraLojaVirtualDTO, HttpStatus.OK);
    }

}

