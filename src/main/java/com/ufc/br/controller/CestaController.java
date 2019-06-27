package com.ufc.br.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Pedido;
import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Prato;
import com.ufc.br.service.PedidoService;

@Controller
@Scope("session")
public class CestaController {

	@Autowired
	PedidoService pedidoService;
	
	private Pessoa pessoa = (Pessoa) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	List<Prato> pratosCesta = new ArrayList<Prato>();
	
	private float calcularValor() {
		float total = 0;
		for(Prato prato:pratosCesta) {
			total += prato.getPreco();
		}
		return total;
	}

	@RequestMapping("/cesta")
	public ModelAndView cesta() {
		ModelAndView mv = new ModelAndView("cesta");
		mv.addObject("pratos", pratosCesta);
		return mv;
	}

	@RequestMapping("/adicionarCesta/{prato}")
	public ModelAndView adicionarCesta(@PathVariable Prato prato) {
		pratosCesta.add(prato);
		ModelAndView mv = new ModelAndView("redirect:/listarPratos");
		return mv;
	}

	@RequestMapping("/removerCesta/{id}")
	public ModelAndView removerCesta(@PathVariable Long id) {
		for (int i = 0; i < pratosCesta.size(); i++) {
			if (id == pratosCesta.get(i).getId()) {
				pratosCesta.remove(i);
				break;
			}
		}
		ModelAndView mv = new ModelAndView("redirect:/cesta");
		return mv;
	}
	@RequestMapping("/confirmarPedido")
	public ModelAndView confirmarPedido() {
		Pedido pedido = new Pedido();
		pedido.setPessoa(pessoa);
		pedido.setPratos(pratosCesta);
		pedido.setValor(calcularValor());
		pedidoService.salvarPedido(pedido);
		pedidoService.sendMail(pessoa, pedido);
		pratosCesta.clear();
		ModelAndView mv = new ModelAndView("redirect:/historico");
		return mv;
	}
	@RequestMapping("/historico")
	public ModelAndView historico() {
		ModelAndView mv = new ModelAndView("/historico");
		mv.addObject("historico", pedidoService.historico(pessoa));
		return mv;
	}
	
	
}
