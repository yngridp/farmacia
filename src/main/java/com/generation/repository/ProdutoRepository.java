package com.generation.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome")String Nome);
    
    List<Produto>findAllByNomeContainingIgnoreCaseAndFabricanteContainingIgnoreCase(@Param ("nome")String nome, @Param ("fabricante")String fabricante);
    
    List<Produto>findAllByNomeContainingIgnoreCaseOrFabricanteContainingIgnoreCase(@Param("nome")String nome, @Param("fabricante")String fabricante);
    
    List<Produto>findAllByPrecoBetween(@Param("inicio")BigDecimal Inicio, @Param("fim")BigDecimal Fim);

}
