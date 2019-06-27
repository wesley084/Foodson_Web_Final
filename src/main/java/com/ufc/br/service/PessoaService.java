package com.ufc.br.service;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Pessoa;
import com.ufc.br.repository.PessoaRepository;

@Service
public class PessoaService implements UserDetailsService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private JavaMailSender mailSender;

	public boolean cadastrar(Pessoa pessoa) {
		pessoa.setSenha(new BCryptPasswordEncoder().encode(pessoa.getSenha()));
		pessoaRepository.save(pessoa);
		return true;
	}

	public List<Pessoa> listarPessoas() {
		return pessoaRepository.findAll();

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return pessoaRepository.findByEmail(username);
	}

	public void sendMail(Pessoa pessoa) {
		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(""+pessoa.getEmail());
			helper.setSubject("Bem Vindo "+pessoa.getNome().split(" ")[0]);
			helper.setText("<p>Olá "+pessoa.getNome().split(" ")[0]+", parabéns por ter se cadastrado no Foodson!</p>", true);
			mailSender.send(mail);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
