package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entidades.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
	Categoria findByNome(String nome);

}
