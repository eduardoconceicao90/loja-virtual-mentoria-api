package com.eduardo.lojavirtual;

import com.eduardo.lojavirtual.controller.AcessoController;
import com.eduardo.lojavirtual.model.Acesso;
import com.eduardo.lojavirtual.repository.AcessoRepository;
import com.eduardo.lojavirtual.service.AcessoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests {

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
		acessoController.salvarAcesso(acesso);
	}

}
