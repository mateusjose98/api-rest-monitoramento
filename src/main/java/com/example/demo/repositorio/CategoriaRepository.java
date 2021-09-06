package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entidades.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
