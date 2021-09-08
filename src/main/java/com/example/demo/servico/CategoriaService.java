package com.example.demo.servico;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entidades.Categoria;
import com.example.demo.excecao.RegraNegocioException;
import com.example.demo.repositorio.CategoriaRepository;

@Service
@Transactional
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> listarTodas() {
		return categoriaRepository.findAll();
	}

	public Optional<Categoria> buscarPorCodigo(Long codigo) {
		return categoriaRepository.findById(codigo);
	}

	public Categoria salver(Categoria categoria) {
		validarCategoriaDuplicada(categoria);
		return categoriaRepository.save(categoria);
	}

	public Categoria atualizat(Long codigo, Categoria categoria) {
		validarCategoriaDuplicada(categoria);
		Categoria cat = validarCategoria(codigo);
		cat.setNome(categoria.getNome());
		return categoriaRepository.save(cat);

	}

	private Categoria validarCategoria(Long codigo) {
		return buscarPorCodigo(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));

	}

	public void deletar(Long codigo) {
		categoriaRepository.deleteById(codigo);
	}

	private void validarCategoriaDuplicada(Categoria categoria) {
		Categoria cat = categoriaRepository.findByNome(categoria.getNome());
		if (cat != null && ! cat.getId().equals(categoria.getId())) {
			throw new RegraNegocioException(String.format("A categoria %s já está cadastrada!", categoria.getNome().toUpperCase()));
		}
	}
}
