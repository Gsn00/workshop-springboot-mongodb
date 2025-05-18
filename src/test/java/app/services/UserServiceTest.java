package app.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.domain.User;
import app.repositories.UserRepository;
import app.services.exception.ObjectNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	UserService service;
	
	@Mock
	UserRepository userRepository;
	
	User u1;
	User u2;
	User u3;
	List<User> users = new ArrayList<>();
	
	@BeforeEach
	void setup() {
		u1 = new User("1", "Maria Brown", "maria@gmail.com");
		u2 = new User("2", "Alex Green", "alex@gmail.com");
		u3 = new User("3", "Bob Grey", "bob@gmail.com");
		
		users.clear();
		
		users.add(u1);
		users.add(u2);
		users.add(u3);
	}
	
	@Test
	void deveBuscarTodosUsuariosComSucesso() {
		when(userRepository.findAll()).thenReturn(users);
		List<User> retorno = service.findAll();
		
		assertEquals(users.size(), retorno.size());
		verify(userRepository).findAll();
		verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	void deveBuscarUmUsuarioPorIdComSucesso() {
		when(userRepository.findById(u1.getId())).thenReturn(Optional.of(u1));
		User retorno = service.findById(u1.getId());
		
		assertEquals(u1.getId(), retorno.getId());
		verify(userRepository).findById(u1.getId());
		verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	void naoDeveEncontrarUsuarioInexistente() {
		assertThrows(IllegalArgumentException.class, () -> {
			service.findById(null);
		});
	}
	
	@Test
	void naoDeveEncontrarUsuarioComIdInexistente() {
		assertThrows(ObjectNotFoundException.class, () -> {
			service.findById("4");
		});
	}
	
	@Test
	void deveInserirUmUsuarioComSucesso() {
		User insertUser = new User(null, "nome", "email");
		when(userRepository.insert(any(User.class))).thenReturn(u1);	
		User retorno = service.insert(insertUser);
		
		assertEquals(u1.getId(), retorno.getId());
		assertEquals(u1.getName(), retorno.getName());
		assertEquals(u1.getEmail(), retorno.getEmail());
		verify(userRepository).insert(any(User.class));
		verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	void naoDeveInserirUsuarioComNomeVazio() {
		assertThrows(IllegalArgumentException.class, () -> {
			User insertUser = new User(null, "", "email");
			service.insert(insertUser);
		});
	}
	
	@Test
	void naoDeveInserirUsuarioComEmailVazio() {
		assertThrows(IllegalArgumentException.class, () -> {
			User insertUser = new User(null, "nome", "");
			service.insert(insertUser);
		});
	}
	
	@Test
	void naoDeveInserirUsuarioComParametrosNulos() {
		assertThrows(IllegalArgumentException.class, () -> {
			User insertUser = new User(null, null, null);
			service.insert(insertUser);
		});
	}
	
	@Test
	void deveAtualizarUsuarioComSucesso() {
		User newUser = new User(u1.getId(), "atualizado", "atualizado@gmail.com");
		
		when(userRepository.findById(newUser.getId())).thenReturn(Optional.of(u1));
		when(userRepository.save(any(User.class))).thenReturn(newUser);
		
		User retorno = service.update(newUser);
		
		assertEquals("atualizado", retorno.getName());
		assertEquals("atualizado@gmail.com", retorno.getEmail());
		
		verify(userRepository).findById(newUser.getId());
		verify(userRepository).save(any(User.class));
		verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	void naoDeveAtualizarUsuarioComNomeVazio() {
		User newUser = new User(u1.getId(), "", "atualizado@gmail.com");
		when(userRepository.findById(newUser.getId())).thenReturn(Optional.of(u1));
		
		assertThrows(IllegalArgumentException.class, () -> {
			service.update(newUser);
		});
	}
	
	@Test
	void naoDeveAtualizarUsuarioComEmailVazio() {
		User newUser = new User(u1.getId(), "atualizado", "");
		when(userRepository.findById(newUser.getId())).thenReturn(Optional.of(u1));
		
		assertThrows(IllegalArgumentException.class, () -> {
			service.update(newUser);
		});
	}
	
	@Test
	void naoDeveAtualizarUsuarioComNomeOuEmailNulo() {
		User newUser = new User(u1.getId(), null, null);
		when(userRepository.findById(newUser.getId())).thenReturn(Optional.of(u1));
		
		assertThrows(IllegalArgumentException.class, () -> {
			service.update(newUser);
		});
	}
	
	@Test
	void naoDeveAtualizarUsuarioInexistente() {
		User newUser = new User("4", "atualizado", "atualizado@gmail.com");		
		assertThrows(ObjectNotFoundException.class, () -> {
			service.update(newUser);
		});
	}
	
	@Test
	void deveDeletarUmUsuarioComSucesso() {
		when(userRepository.findById(u1.getId())).thenReturn(Optional.of(u1));
		
		assertDoesNotThrow(() -> {
			service.delete(u1.getId());
		});
		verify(userRepository).findById(u1.getId());
		verify(userRepository).deleteById(u1.getId());
		verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	void naoDeveDeletarUsuarioInexistente() {
		when(userRepository.findById("4")).thenReturn(Optional.empty());
		
		assertThrows(ObjectNotFoundException.class, () -> {
			service.delete("4");
		});
		
		verify(userRepository).findById("4");
		verify(userRepository, never()).deleteById(any());
		verifyNoMoreInteractions(userRepository);
	}
}
