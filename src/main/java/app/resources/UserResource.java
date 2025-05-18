package app.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.domain.Post;
import app.domain.User;
import app.dto.UserDTO;
import app.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@Operation(description = "Retorna todos os usuários cadastrados no banco de dados.")
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Retorna a lista de usuários"))
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = userService.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).toList();
		return ResponseEntity.ok().body(listDto);
	}
	
	@Operation(description = "Retorna um usuário pelo ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna o usuário"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado")
	})
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User user = userService.findById(id);
		UserDTO userDto = new UserDTO(user);
		return ResponseEntity.ok().body(userDto);
	}
	
	@Operation(description = "Insere um novo usuário ao banco de dados")
	@ApiResponses(value = @ApiResponse(responseCode = "201", description = "Usuário inserido com sucesso"))
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDTO dto) {
		User user = userService.fromDto(dto);
		user = userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@Operation(description = "Deleta um usuário do banco de dados")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(description = "Atualiza um usuário no banco de dados")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Usuário atualizado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable String id, @RequestBody UserDTO dto) {
		User user = userService.fromDto(dto);
		user.setId(id);
		user = userService.update(user);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(description = "Retorna todos os posts de um usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna uma lista com os posts do usuário"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado")
	})
	@GetMapping("/{id}/posts")
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
		User user = userService.findById(id);
		return ResponseEntity.ok().body(user.getPosts());
	}
}
