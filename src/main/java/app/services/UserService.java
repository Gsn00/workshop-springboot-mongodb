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
	
	public User insert(User obj) {
		return userRepository.insert(obj);
	}
	
	public void delete(String id) {
		//Caso não exista o usuário, lança uma exception
		findById(id);
		userRepository.deleteById(id);
	}
	
	public User update(User user) {
		User newUser = userRepository.findById(user.getId()).get();
		updateData(newUser, user);
		return userRepository.save(newUser);
	}
	
	private void updateData(User newUser, User user) {
		newUser.setName(user.getName());
		newUser.setEmail(user.getEmail());
	}

	public User fromDto(UserDTO dto) {
		User user = new User(dto.getId(), dto.getName(), dto.getEmail());
		return user;
	}
}
