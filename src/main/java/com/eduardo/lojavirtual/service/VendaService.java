package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.VendaCompraLojaVirtual;
import com.eduardo.lojavirtual.repository.VendaCompraLojaVirtualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;

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

    public List<VendaCompraLojaVirtual> consultaVendaFaixaData(String data1, String data2) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = dateFormat.parse(data1);
        Date date2 = dateFormat.parse(data2);

        return vendaCompraLojaVirtualRepository.consultaVendaFaixaData(date1, date2);
    }

}
