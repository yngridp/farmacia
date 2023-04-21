package com.generation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.model.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
	List<Categoria> findAllByMedicamentoContainingIgnoreCase(@Param("medicamento")String Medicamento);
	
	List<Categoria> findAllByDermocosmeticoContainingIgnoreCase(@Param("dermocosmetico")String Dermocosmetico);

}
