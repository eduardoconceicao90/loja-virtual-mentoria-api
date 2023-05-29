package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.dto.ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO;
import com.eduardo.lojavirtual.model.dto.ObejtoRequisicaoRelatorioProdutoAlertaEstoqueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotaFiscalCompraService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
        Este relatório permite saber os produtos comprados para serem vendidos pela loja virtual,
        todos os produtos tem relação com a nota fiscal de compra/venda.
    */

    public List<ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO> gerarRelatorioProdCompraNota(
            ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO obejtoRequisicaoRelatorioProdCompraNotaFiscalDto) {

        List<ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO> retorno = new ArrayList<>();

        String sql = "select p.id as codigoProduto, p.nome as nomeProduto, "
                + " p.valor_venda as valorVendaProduto, ntp.quantidade as quantidadeComprada, "
                + " pj.id as codigoFornecedor, pj.nome as nomeFornecedor, nfc.data_compra as dataCompra "
                + " from nota_fiscal_compra as nfc "
                + " inner join nota_item_produto as ntp on  nfc.id = nota_fiscal_compra_id "
                + " inner join produto as p on p.id = ntp.produto_id "
                + " inner join pessoa_juridica as pj on pj.id = nfc.pessoa_id where ";

        sql += " nfc.data_compra >= '"+obejtoRequisicaoRelatorioProdCompraNotaFiscalDto.getDataInicial()+"' and ";
        sql += " nfc.data_compra <= '" + obejtoRequisicaoRelatorioProdCompraNotaFiscalDto.getDataFinal() +"' ";

        if (!obejtoRequisicaoRelatorioProdCompraNotaFiscalDto.getCodigoNota().isEmpty()) {
            sql += " and nfc.id = " + obejtoRequisicaoRelatorioProdCompraNotaFiscalDto.getCodigoNota() + " ";
        }

        if (!obejtoRequisicaoRelatorioProdCompraNotaFiscalDto.getCodigoProduto().isEmpty()) {
            sql += " and p.id = " + obejtoRequisicaoRelatorioProdCompraNotaFiscalDto.getCodigoProduto() + " ";
        }

        if (!obejtoRequisicaoRelatorioProdCompraNotaFiscalDto.getNomeProduto().isEmpty()) {
            sql += " upper(p.nome) like upper('%"+obejtoRequisicaoRelatorioProdCompraNotaFiscalDto.getNomeProduto()+"')";
        }

        if (!obejtoRequisicaoRelatorioProdCompraNotaFiscalDto.getNomeFornecedor().isEmpty()) {
            sql += " upper(pj.nome) like upper('%"+obejtoRequisicaoRelatorioProdCompraNotaFiscalDto.getNomeFornecedor()+"')";
        }

        retorno = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ObejtoRequisicaoRelatorioProdCompraNotaFiscalDTO.class));

        return retorno;
    }

    /*
        Este relatório retorna os produtos que estão com estoque menor ou igual a quantidade definida no campo
        qtd_alerta_estoque.
    */
    public List<ObejtoRequisicaoRelatorioProdutoAlertaEstoqueDTO> gerarRelatorioAlertaEstoque(
            ObejtoRequisicaoRelatorioProdutoAlertaEstoqueDTO alertaEstoque){

        List<ObejtoRequisicaoRelatorioProdutoAlertaEstoqueDTO> retorno = new ArrayList<>();

        String sql = "select p.id as codigoProduto, p.nome as nomeProduto, "
                + " p.valor_venda as valorVendaProduto, ntp.quantidade as quantidadeComprada, "
                + " pj.id as codigoFornecedor, pj.nome as nomeFornecedor, nfc.data_compra as dataCompra, "
                + " p.qtd_estoque as qtdEstoque, p.qtd_alerta_estoque as qtdAlertaEstoque "
                + " from nota_fiscal_compra as nfc "
                + " inner join nota_item_produto as ntp on  nfc.id = nota_fiscal_compra_id "
                + " inner join produto as p on p.id = ntp.produto_id "
                + " inner join pessoa_juridica as pj on pj.id = nfc.pessoa_id where ";

        sql += " nfc.data_compra >='"+alertaEstoque.getDataInicial()+"' and ";
        sql += " nfc.data_compra <= '" + alertaEstoque.getDataFinal() +"' ";
        sql += " and p.alerta_qtd_estoque = true and p.qtd_estoque <= p.qtd_alerta_estoque ";

        if (!alertaEstoque.getCodigoNota().isEmpty()) {
            sql += " and nfc.id = " + alertaEstoque.getCodigoNota() + " ";
        }

        if (!alertaEstoque.getCodigoProduto().isEmpty()) {
            sql += " and p.id = " + alertaEstoque.getCodigoProduto() + " ";
        }

        if (!alertaEstoque.getNomeProduto().isEmpty()) {
            sql += " upper(p.nome) like upper('%"+alertaEstoque.getNomeProduto()+"')";
        }

        if (!alertaEstoque.getNomeFornecedor().isEmpty()) {
            sql += " upper(pj.nome) like upper('%"+alertaEstoque.getNomeFornecedor()+"')";
        }

        retorno = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ObejtoRequisicaoRelatorioProdutoAlertaEstoqueDTO.class));

        return retorno;
    }

}
