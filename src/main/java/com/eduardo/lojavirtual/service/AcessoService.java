package com.eduardo.lojavirtual.service;

import com.eduardo.lojavirtual.model.Acesso;
import com.eduardo.lojavirtual.repository.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcessoService {

    @Autowired
    private AcessoRepository acessoRepository;

    public Acesso save(Acesso acesso){
        return acessoRepository.save(acesso);
    }

    public void delete(Long id){
        acessoRepository.deleteById(id);
    }

}
