package com.thiago.mensageria.service.imple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiago.mensageria.modal.Mensagem;
import com.thiago.mensageria.repository.IMensagemRepository;
import com.thiago.mensageria.service.IMensagemService;

@Service
public class MensagemService implements IMensagemService{
	
	private IMensagemRepository repository;

	@Autowired
	public MensagemService(IMensagemRepository repo) {
		this.repository = repo;
	}

	@Override
	public void salvar(Mensagem mensagem) {
		repository.save(mensagem);
	}
	
}
