package com.generation.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.generation.model.Produto;
import com.generation.repository.CategoriaRepository;
import com.generation.repository.ProdutoRepository;
import jakarta.validation.Valid;


@RestController 
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*" )
public class ProdutoController {

	@Autowired // injeção de dependecia
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	//CRUD
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){ 
		return ResponseEntity.ok(produtoRepository.findAll());
}
	@GetMapping("/{id}") 
	public ResponseEntity<Produto> getById(@PathVariable Long id){ 
		return produtoRepository.findById(id) 
		      .map(resposta -> ResponseEntity.ok(resposta)) 
		      .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
}
	@GetMapping("/nome/{nome}") 
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
}
	@PostMapping
	public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto){
		return categoriaRepository.findById(produto.getCategoria().getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto)))
				.orElse(ResponseEntity.badRequest().build());
}
	@PutMapping 
	   public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto){ 
		   return produtoRepository.findById(produto.getId())
					.map(resposta -> ResponseEntity.ok().body(produtoRepository.save(produto)))
					.orElse(ResponseEntity.notFound().build());
	   
}
	 @ResponseStatus(HttpStatus.NO_CONTENT) 
		@DeleteMapping("/{id}") 
		public void delete(@PathVariable Long id) { 
			
			Optional<Produto> produto = produtoRepository.findById(id);
			
			if(produto.isEmpty())
				 throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			produtoRepository.deleteById(id);
}
	 @GetMapping("/nome_fabricante/{nome}/{fabricante}")
	    public ResponseEntity<List<Produto>> getByNomeFabricante(@PathVariable String nome, @PathVariable String fabricante){
	        return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCaseAndFabricanteContainingIgnoreCase(nome, fabricante));
	        
	    }
	    
	    @GetMapping("/nome_ou_fabricante/{nome}/{fabricante}")
	    public ResponseEntity<List<Produto>> getByNomeOuFabricante(@PathVariable String nome, @PathVariable String fabricante){
	        return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCaseOrFabricanteContainingIgnoreCase(nome, fabricante));    
	    }
	    @GetMapping("preco_inicial/{inicio}/preco_final/{fim}")
	    ResponseEntity<List<Produto>> getPrecoBetween(@PathVariable BigDecimal inicio,@PathVariable BigDecimal fim){
	        return ResponseEntity.ok(produtoRepository.findAllByPrecoBetween(inicio,fim));    
	  }
}
