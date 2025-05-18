package app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.User;
import app.dto.UserDTO;
import app.repositories.UserRepository;
import app.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findById(String id) {
		if (id == null) throw new IllegalArgumentException("ID inválido.");
		
		return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
	}
	
	public User insert(User user) {
		if (user.getName() == null || user.getName().isBlank()) throw new IllegalArgumentException("Atributos inválidos");
		if (user.getEmail() == null || user.getEmail().isBlank()) throw new IllegalArgumentException("Atributos inválidos");
		
		return userRepository.insert(user);
	}
	
	public void delete(String id) {
		//Caso não exista o usuário, lança uma exception
		findById(id);
		userRepository.deleteById(id);
	}
	
	public User update(User user) {
		User newUser = findById(user.getId());
		updateData(newUser, user);
		return userRepository.save(newUser);
	}
	
	private void updateData(User newUser, User user) {
		if (user.getName() == null || user.getName().isBlank()) throw new IllegalArgumentException("Atributos inválidos");
		if (user.getEmail() == null || user.getEmail().isBlank()) throw new IllegalArgumentException("Atributos inválidos");
		
		newUser.setName(user.getName());
		newUser.setEmail(user.getEmail());
	}

	public User fromDto(UserDTO dto) {
		User user = new User(dto.getId(), dto.getName(), dto.getEmail());
		return user;
	}
}
