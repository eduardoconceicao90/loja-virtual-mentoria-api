package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.VendaCompraLojaVirtual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    public void exclusaoTotalVendaBanco2(Long idVenda) {

        String sql = "begin; UPDATE vd_cp_loja_virt set excluido = true where id = " + idVenda + "; commit;";

        jdbcTemplate.execute(sql);
    }

    public void exclusaoTotalVendaBanco(Long idVenda) {

        String sql =
                          " begin;"
                        + " UPDATE nota_fiscal_venda set venda_compra_loja_virt_id = null where venda_compra_loja_virt_id = "+idVenda+"; "
                        + " delete from nota_fiscal_venda where venda_compra_loja_virt_id = "+idVenda+"; "
                        + " delete from item_venda_loja where venda_compra_loja_virt_id = "+idVenda+"; "
                        + " delete from status_rastreio where venda_compra_loja_virt_id = "+idVenda+"; "
                        + " delete from vd_cp_loja_virt where id = "+idVenda+"; "
                        + " commit; ";

        jdbcTemplate.execute(sql);
    }

    public void ativaRegistroVendaBanco(Long idVenda) {

        String sql = "begin; UPDATE vd_cp_loja_virt set excluido = false where id = " + idVenda + "; commit;";

        jdbcTemplate.execute(sql);
    }

    /*HQL (Hibernate) ou JPQL (JPA ou Spring Data)*/
    public List<VendaCompraLojaVirtual> consultaVendaFaixaData(String data1, String data2){

        String sql = "select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i "
                + " where i.vendaCompraLojaVirtual.excluido = false "
                + " and i.vendaCompraLojaVirtual.dataVenda >= '" + data1 + "'"
                + " and i.vendaCompraLojaVirtual.dataVenda <= '" + data2 + "'";

        return entityManager.createQuery(sql).getResultList();
    }

}
