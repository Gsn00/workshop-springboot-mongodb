package app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
	
	User u1 = new User("1", "Maria Brown", "maria@gmail.com");
	User u2 = new User("2", "Alex Green", "alex@gmail.com");
	User u3 = new User("3", "Bob Grey", "bob@gmail.com");
	List<User> users = new ArrayList<>();
	
	@BeforeEach
	public void setup() {
		users.clear();
		
		users.add(u1);
		users.add(u2);
		users.add(u3);
	}
	
	@Test
	public void deveBuscarTodosUsuariosComSucesso() {
		when(userRepository.findAll()).thenReturn(users);
		List<User> retorno = service.findAll();
		
		assertEquals(users.size(), retorno.size());
		verify(userRepository).findAll();
		verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void deveBuscarUmUsuarioPorIdComSucesso() {
		when(userRepository.findById(u1.getId())).thenReturn(Optional.of(u1));
		User retorno = service.findById(u1.getId());
		
		assertEquals(u1.getId(), retorno.getId());
		verify(userRepository).findById(u1.getId());
		verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void naoDeveRetornarUsuarioCasoIdSejaNulo() {
		assertThrows(IllegalArgumentException.class, () -> {
			service.findById(null);
		});
	}
	
	@Test
	public void deveLancarExceptionCasoIdNaoExista() {
		assertThrows(ObjectNotFoundException.class, () -> {
			service.findById("4");
		});
	}
}
