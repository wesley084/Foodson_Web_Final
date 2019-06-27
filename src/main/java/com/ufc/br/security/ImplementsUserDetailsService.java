package com.ufc.br.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.ufc.br.model.Gerente;
import com.ufc.br.model.Pessoa;
import com.ufc.br.repository.GerenteRepository;
import com.ufc.br.repository.PessoaRepository;

@Repository
public class ImplementsUserDetailsService implements UserDetailsService {

	@Autowired
	private PessoaRepository usuarioRepo;
	
	@Autowired
	private GerenteRepository gerenteRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	
		Gerente gerente = gerenteRepo.findByEmail(email);
		
		if(gerente != null)
			return gerente;
		
		Pessoa usuario = usuarioRepo.findByEmail(email);
		
		if(usuario != null)
			return usuario;
		
		throw new UsernameNotFoundException("Login/Senha nao bate");
	}
}
