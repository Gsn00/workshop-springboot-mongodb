package app.resources;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.domain.Post;
import app.resources.util.URL;
import app.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/posts")
public class PostResource {
	
	@Autowired
	private PostService postService;
	
	@Operation(description = "Retorna todos os posts e seus comentários")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna a lista com posts")})
	@GetMapping
	public ResponseEntity<List<Post>> findAll() {
		List<Post> posts = postService.findAll();
		return ResponseEntity.ok().body(posts);
	}
	
	@Operation(description = "Retorna um post por seu ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna o post com sucesso"),
			@ApiResponse(responseCode = "404", description = "Post não encontrado")
	})
	@GetMapping("/{id}")
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post post = postService.findById(id);
		return ResponseEntity.ok().body(post);
	}
	
	@Operation(description = "Retorna todos os posts contendo um texto específico em seu título. Se for vazio retornará todos os posts.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna a lista com posts")})
	@GetMapping("/titlesearch")
	public ResponseEntity<List<Post>> findByTitle(
			@Parameter(description = "Texto para filtrar", example = "Bom dia!") @RequestParam(defaultValue = "") String text) {
		text = URL.decodeParam(text);
		List<Post> posts = postService.findByTitle(text);
		return ResponseEntity.ok().body(posts);
	}
	
	@Operation(description = "Retorna todos os posts contendo um texto específico em seu título, corpo ou comentários, com data mínima e máxima de postagem")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna a lista com posts")})
	@GetMapping("/fullsearch")
	public ResponseEntity<List<Post>> fullSearch(
			@Parameter(description = "Texto para filtrar", example = "Bom dia!") @RequestParam(defaultValue = "") String text,
			@Parameter(description = "Data mínima", example = "2025-03-20") @RequestParam(defaultValue = "") String minDate,
			@Parameter(description = "Data máxima", example = "2025-03-25") @RequestParam(defaultValue = "") String maxDate) {
		text = URL.decodeParam(text);
		Date min = URL.convertDate(minDate, new Date(0L));
		Date max = URL.convertDate(maxDate, new Date());
		List<Post> posts = postService.fullSearch(text, min, max);
		return ResponseEntity.ok().body(posts);
	}
}
