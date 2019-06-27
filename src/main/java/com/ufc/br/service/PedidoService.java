package com.ufc.br.service;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Pedido;
import com.ufc.br.model.Pessoa;
import com.ufc.br.repository.PedidoRepository;

@Service
public class PedidoService {
	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	private JavaMailSender mailSender;

	public void salvarPedido(Pedido pedido) {
		pedidoRepository.save(pedido);
	}

	public List<Pedido> historico(Pessoa pessoa) {
		return pedidoRepository.findByPessoa(pessoa);
	}

	public void sendMail(Pessoa pessoa, Pedido pedido) {
		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo("" + pessoa.getEmail());
			helper.setSubject("Pedido de " + pessoa.getNome().split(" ")[0]);
			helper.setText("<p>Olá " + pessoa.getNome().split(" ")[0]
					+ ", você acabou de realizar um pedido no Foodson no valor de $" + pedido.getValor()
					+ " para mais informações entre no nosso site!</p><p></p><p>Itens do pedido: "
					+ pedido.mostrarPratos() + "</p>", true);
			mailSender.send(mail);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
