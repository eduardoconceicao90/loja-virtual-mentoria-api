package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.model.VendaCompraLojaVirtual;
import com.eduardo.lojavirtual.model.dto.webMania.*;
import com.eduardo.lojavirtual.repository.VendaCompraLojaVirtualRepository;
import com.eduardo.lojavirtual.service.WebManiaNotaFiscalService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TesteWebMania {

    @Autowired
    private WebManiaNotaFiscalService webManiaNotaFiscalService;

    @Autowired
    private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;

    @Test
    public void testeEmissaoNota() throws Exception {
        emiteNotaFiscal();
    }

    @Test
    public String emiteNotaFiscal() throws Exception {

        NotaFiscalEletronicaDTO notaFiscalEletronica = new NotaFiscalEletronicaDTO();

        /* Dados da Nota */
        notaFiscalEletronica.setID("1");
        notaFiscalEletronica.setUrl_notificacao(""); // WebHook
        notaFiscalEletronica.setOperacao(1);
        notaFiscalEletronica.setNatureza_operacao("Venda de produto");
        notaFiscalEletronica.setModelo("1");
        notaFiscalEletronica.setFinalidade(1);
        notaFiscalEletronica.setAmbiente(2);

        /* Dados do Cliente */
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNome_completo("Eduardo Conceição");
        cliente.setEmail("eduardo@mail.com");
        cliente.setCpf("10055588896");
        cliente.setEndereco("Rua Teixeira Soares");
        cliente.setComplemento("N/A");
        cliente.setNumero("303");
        cliente.setBairro("Timbi");
        cliente.setCidade("Camaragibe");
        cliente.setUf("PE");

        notaFiscalEletronica.setCliente(cliente);

        /* Dados do Produto */
        List<ProdutoDTO> produtos = new ArrayList<>();
        ProdutoDTO produto = new ProdutoDTO();
        produto.setNome("Capa Redmi 10");
        produto.setCodigo("5879");
        produto.setNcm("6109.10.00");
        produto.setCest("28.038.00");
        produto.setQuantidade(1);
        produto.setUnidade("UN");
        produto.setPeso("0.200");
        produto.setOrigem(0);
        produto.setSubtotal("15.00");
        produto.setTotal("15.00");
        produto.setClasse_imposto("REF1000");

        ProdutoDTO produto2 = new ProdutoDTO();
        produto2.setNome("Cabo tipo C");
        produto2.setCodigo("1119");
        produto2.setNcm("6109.10.00");
        produto2.setCest("28.038.00");
        produto2.setQuantidade(1);
        produto2.setUnidade("UN");
        produto2.setPeso("0.200");
        produto2.setOrigem(0);
        produto2.setSubtotal("15.00");
        produto2.setTotal("15.00");
        produto2.setClasse_imposto("REF1000");

        produtos.add(produto);
        produtos.add(produto2);
        notaFiscalEletronica.setProdutos(produtos);

        /* Dados do Pedido */
        PedidoDTO pedido = new PedidoDTO();
        pedido.setPagamento(0);
        pedido.setPresenca(2);
        pedido.setModalidade_frete(0);
        pedido.setFrete("12.56");
        pedido.setDesconto("10.00");
        pedido.setTotal("32.56");

        notaFiscalEletronica.setPedido(pedido);

        String retorno = webManiaNotaFiscalService.emitirNotaFiscal(notaFiscalEletronica);
        System.out.println("---------> Retorno Emissão Nota Fiscal: " + retorno);
        return retorno;
    }

    @Test
    public void TesteCancelaNotaFiscal() throws Exception {

        String retorno = webManiaNotaFiscalService.cancelarNotaFiscal("000000000000000000000000","Cancelamento teste");
        System.out.println("---------> Retorno Cancelamento Nota Fiscal: " + retorno);

    }

    @Test
    public void TesteConsultaNotaFiscal() throws Exception {

        String retorno = webManiaNotaFiscalService.consultarNotaFiscal("000000000000000000000000");
        System.out.println("---------> Retorno Consulta Nota Fiscal: " + retorno);

    }

    @Test
    public void testeGravaNotaNoBanco() throws Exception {

        String json = emiteNotaFiscal();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        ObjetoEmissaoNotaFiscalWebManiaDTO notaFiscalWebMania = objectMapper
                .readValue(json, new TypeReference<ObjetoEmissaoNotaFiscalWebManiaDTO>() {});

        VendaCompraLojaVirtual vendaCompraLojaVirtual = vendaCompraLojaVirtualRepository.findById(25L).get();

        webManiaNotaFiscalService.gravaNotaParaVenda(notaFiscalWebMania, vendaCompraLojaVirtual);

    }

}
