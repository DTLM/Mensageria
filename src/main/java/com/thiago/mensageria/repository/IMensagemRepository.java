package com.thiago.mensageria.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thiago.mensageria.modal.Mensagem;

@Repository
public interface IMensagemRepository extends CrudRepository<Mensagem, Long>{

}
