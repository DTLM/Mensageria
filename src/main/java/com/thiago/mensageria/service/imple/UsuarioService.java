package com.thiago.mensageria.service.imple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiago.mensageria.modal.Mensagem;
import com.thiago.mensageria.repository.IMensagemRepository;
import com.thiago.mensageria.service.IMensagemService;
import com.thiago.mensageria.service.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService{
	
	private IMensagemRepository repository;

	@Autowired
	public UsuarioService(IMensagemRepository repo) {
		this.repository = repo;
	}

}
