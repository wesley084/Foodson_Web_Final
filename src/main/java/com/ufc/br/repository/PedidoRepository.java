package com.ufc.br.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufc.br.model.Pedido;
import com.ufc.br.model.Pessoa;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	List<Pedido> findByPessoa(Pessoa pessoa);
}
