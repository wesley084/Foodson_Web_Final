package com.ufc.br.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Prato;
import com.ufc.br.service.PessoaService;
import com.ufc.br.service.PratoService;


@Controller
public class ProjetoController {
	
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private PratoService pratoService;

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/cadastroPessoa")
	public String cadastro() {
		return "cadastroPessoa";
	}
	
	@RequestMapping("/loginEntrar")
	public ModelAndView loginEntrar(Pessoa pessoa){
		ModelAndView mv = new ModelAndView("redirect:/index");
		return mv;
	}
	
	@RequestMapping("/contato")
	public String contato() {
		return "contato";
	}
	
	@RequestMapping("/cadastroPrato")
	public ModelAndView cadastroPrato() {
		ModelAndView mv = new ModelAndView("/cadastroPrato");
		mv.addObject("prato", new Prato());
		return mv;
	}
	@RequestMapping("/salvarPrato")
	public ModelAndView salvarPrato(Prato prato, @RequestParam(value= "imagem") MultipartFile imagem) {
		pratoService.cadastrar(prato, imagem);
		ModelAndView mv = new ModelAndView("redirect:/listarPratos");
		return mv;
	}
	@RequestMapping("/listarPratos")
	public ModelAndView listarPratos() {
		List<Prato> pratos = pratoService.listarPratos();
		ModelAndView mv = new ModelAndView("listaPratos");
		mv.addObject("listaDePratos", pratos);
		
		return mv;
	}
	
	@RequestMapping("/salvarPessoa")
	public String salvarPessoa(Pessoa pessoa) {
		pessoaService.cadastrar(pessoa);
		pessoaService.sendMail(pessoa);
		return "index";
	}
	@RequestMapping("/excluirPrato/{id}")
	public ModelAndView excluirPrato (@PathVariable Long id) {
		pratoService.excluirPrato(id);
		ModelAndView mv = new ModelAndView("redirect:/listarPratos");
		return mv;
	}
	@RequestMapping("/editarPrato/{id}")
	public ModelAndView editarPrato (@PathVariable Long id) {
		Prato prato = pratoService.buscarPrato(id);
		ModelAndView mv = new ModelAndView("cadastroPrato");
		mv.addObject(prato);
		return mv;
	}
}
