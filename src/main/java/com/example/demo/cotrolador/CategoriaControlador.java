package com.example.demo.cotrolador;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entidades.Categoria;
import com.example.demo.servico.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaControlador {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	private List<Categoria> listarTodas() {
		return categoriaService.listarTodas();
	}

	@GetMapping("/{codigo}")
	private ResponseEntity<Optional<Categoria>> buscarPorId(@PathVariable Long codigo) {
		return categoriaService.buscarPorCodigo(codigo).isPresent()
				? ResponseEntity.ok(categoriaService.buscarPorCodigo(codigo))
				: ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private ResponseEntity<Categoria> salvar(@RequestBody @Valid Categoria categoria) {
		return ResponseEntity.ok(categoriaService.salver(categoria));
	}

	@PutMapping(value = "/{codigo}") @ResponseStatus(code = HttpStatus.NO_CONTENT)
	private Categoria atualizar(@PathVariable Long codigo, @RequestBody @Valid Categoria categoria) {
		return categoriaService.atualizat(codigo, categoria);
	}
}
