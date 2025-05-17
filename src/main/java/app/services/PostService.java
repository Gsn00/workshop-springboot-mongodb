package app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Post;
import app.repositories.PostRepository;
import app.services.exception.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	public Post findById(String id) {
		return postRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Post não encontrado"));
	}
}
