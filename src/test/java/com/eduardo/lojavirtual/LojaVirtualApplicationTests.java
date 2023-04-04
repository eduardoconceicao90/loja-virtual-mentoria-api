package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.controller.AcessoController;
import com.eduardo.lojavirtual.model.Acesso;
import com.eduardo.lojavirtual.repository.AcessoRepository;
import com.eduardo.lojavirtual.service.AcessoService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Locale;

@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests extends TestCase {

	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private AcessoService acessoService;

	@Autowired
	private AcessoController acessoController;

	@Test
	public void testeCadastraAcesso(){
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");
		acesso = acessoController.salvarAcesso(acesso).getBody();

		assertEquals(true, acesso.getId() > 0);

		/* Teste de carregamento */
		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();

		assertEquals(acesso.getId(), acesso2.getId());

		/* Teste de delete */
		acessoRepository.deleteById(acesso2.getId());
		acessoRepository.flush(); /* Roda SQL de delete no banco de dados */
		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);

		assertEquals(true,  acesso3 == null);

		/* Teste de query */

		acesso =  new Acesso();
		acesso.setDescricao("ROLE_ALUNO");
		acesso = acessoController.salvarAcesso(acesso).getBody();
		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());

		assertEquals(1, acessos.size());

		acessoRepository.deleteById(acesso.getId());
	}
}
