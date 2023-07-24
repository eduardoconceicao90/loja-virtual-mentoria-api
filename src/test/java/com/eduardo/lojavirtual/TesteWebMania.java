package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.model.dto.webMania.ClienteDTO;
import com.eduardo.lojavirtual.model.dto.webMania.NotaFiscalEletronicaDTO;
import com.eduardo.lojavirtual.model.dto.webMania.PedidoDTO;
import com.eduardo.lojavirtual.model.dto.webMania.ProdutoDTO;
import com.eduardo.lojavirtual.service.WebManiaNotaFiscalService;
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

    @Test
    public void TesteEmissaoNotaFiscal() throws Exception {

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
        produto.setSubtotal("29.90");
        produto.setTotal("29.90");
        produto.setClasse_imposto("REF1000");

        produtos.add(produto);
        notaFiscalEletronica.setProdutos(produtos);

        /* Dados do Pedido */
        PedidoDTO pedido = new PedidoDTO();
        pedido.setPagamento(0);
        pedido.setPresenca(2);
        pedido.setModalidade_frete(0);
        pedido.setFrete("12.56");
        pedido.setDesconto("10.00");
        pedido.setTotal("32.46");

        notaFiscalEletronica.setPedido(pedido);

        String retorno = webManiaNotaFiscalService.emitirNotaFiscal(notaFiscalEletronica);
        System.out.println("---------> Retorno Emissão Nota Fiscal: " + retorno);
    }
}
