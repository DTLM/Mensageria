package com.thiago.mensageria.controler;

import org.apache.pulsar.shade.io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiago.mensageria.modal.Mensagem;
import com.thiago.mensageria.service.IMensagemService;

import jakarta.ws.rs.PathParam;

@RestController
@RequestMapping("/mensagem")
public class MensagemControler {

	@Autowired
	private IMensagemService service;
	
	@PostMapping("/enviar/{qtd}")
	public ResponseEntity enviar(@PathParam("qtd") String quantidade) {
		if(StringUtil.isNullOrEmpty(quantidade)) {
			return new ResponseEntity("Numero impossivel,",HttpStatus.BAD_REQUEST);
		}
		int qtd = Integer.valueOf(quantidade).intValue();
		Mensagem msm = null;
		for(int i= 0; i< qtd; i++) {
			String mensagem = "ola, numero: " + qtd;
			msm = Mensagem.builder().mensagem(mensagem).build();
			service.salvar(msm);
		}
		return new ResponseEntity("enviados!",HttpStatus.OK);
	}
	
	@GetMapping("/receber")
	public ResponseEntity receber() {
		return new ResponseEntity(HttpStatus.OK);
	}
}
