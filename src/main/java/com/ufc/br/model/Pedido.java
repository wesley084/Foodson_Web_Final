package com.ufc.br.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Pedido {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	@ManyToMany 
	private List<Prato> pratos;
	private float valor;
	
	@ManyToOne
	private Pessoa pessoa;
	
	public String mostrarPratos() {
		String pr = "";
		for(Prato prato:pratos) {
			pr = pr.concat(prato.getNome()).concat(", ");
		}
		if (pr.length()>2) {
			pr = pr.substring(0, pr.length()-2);
		}
		return pr;
	}
	
	public List<Prato> retornaPratos(){
		return pratos;
	}
	
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Prato> getPratos() {
		return pratos;
	}
	public void setPratos(List<Prato> pratos) {
		this.pratos = pratos;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	
}
