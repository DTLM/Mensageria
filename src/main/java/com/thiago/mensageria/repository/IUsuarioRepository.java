package com.thiago.mensageria.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thiago.mensageria.modal.Usuario;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario, Long>{

	@Query("SELECT e FROM usuario e JOIN FETCH e.roles where e.username= (:username)")
	public Usuario findByUsername(@Param("username") String username);
}
