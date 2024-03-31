package com.thiago.mensageria.service.imple;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.thiago.mensageria.modal.Usuario;
import com.thiago.mensageria.repository.IUsuarioRepository;
import com.thiago.mensageria.service.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService{
	
	private IUsuarioRepository repository;

	@Autowired
	private BCryptPasswordEncoder bc;
	
	@Autowired
	public UsuarioService(IUsuarioRepository repo) {
		this.repository = repo;
	}
	
	@Override
	public void createUser(Usuario user) throws Exception {
		Usuario usuario = repository.findByUsername(user.getUsername());
		if(usuario != null) {
			throw new Exception("Usuario já cadastrado.");
		}
		usuario = Usuario.builder()
				.username(user.getUsername())
				.password(bc.encode(user.getPassword()))
				.build();
		List<String> autoridades = new ArrayList<>();
		if(user.getRole() != null && user.getRole().getAutoridades() != null && user.getRole().getAutoridades().size() > 0) {
			
		}
		usuario.setRole(null);
		repository.save(usuario);
	}

}
