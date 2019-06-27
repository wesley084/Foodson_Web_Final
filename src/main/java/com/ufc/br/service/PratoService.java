package com.ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ufc.br.model.Prato;
import com.ufc.br.repository.PratoRepository;
import com.ufc.br.util.MyFileUtils;

@Service
public class PratoService {
	
	@Autowired
	private PratoRepository pratoRepository;
	
	public boolean cadastrar(Prato prato, MultipartFile imagem) {
		String caminho = "imagens/" +prato.getNome()+".png";
		MyFileUtils.salvarImagem(caminho,imagem);
		pratoRepository.save(prato);
		return true;
	}

	public List<Prato> listarPratos() {
		return pratoRepository.findAll();
	}
	
	public boolean excluirPrato(Long id) {
		pratoRepository.deleteById(id);
		return true;
	}
	public Prato buscarPrato(Long id) {
		return pratoRepository.getOne(id);
	}
}
