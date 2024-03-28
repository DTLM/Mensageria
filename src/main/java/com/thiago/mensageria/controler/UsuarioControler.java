package com.thiago.mensageria.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiago.mensageria.modal.Usuario;
import com.thiago.mensageria.service.IUsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioControler {

	@Autowired
	private IUsuarioService service;
	
	@PostMapping("/salvar")
	public ResponseEntity salvar(@RequestBody Usuario usuario) {
		if(usuario == null) {
			return new ResponseEntity("Usuario impossivel,",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity("criado!",HttpStatus.OK);
	}
}
