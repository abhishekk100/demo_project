package com.example.demo.user;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/users/")
@CrossOrigin(origins = "*")
public class UserController {

	private final UserRepository repository;
	private final WebClient webClient;
	
	public UserController(UserRepository repository, WebClient webClient) {
		this.repository = repository;
		this.webClient = webClient;
	}
	
	@GetMapping("list")
	public ResponseEntity<Object> getAllUser() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@PostMapping("save")
	public ResponseEntity<Object> saveUser(@RequestBody User user) {		
		return ResponseEntity.ok(repository.save(user));
	}

	@PostMapping("get")
	public ResponseEntity<Object> getUser(@PathVariable Integer id) {		
		return ResponseEntity.ok(repository.findById(id).orElse(null));
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {		
		try {
			repository.deleteById(id);
			return ResponseEntity.ok("Deleted Succefully, User Id: "+ id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok("Failed to delete, User Id: "+ id);
		}
	}
	
	public Mono<Object> sendPostRequestAsync(Object requestBody) {
	        String url = "http://localhost:8081/api/target-endpoint";

	        return webClient.post()
	            .uri(url)
	            .contentType(MediaType.APPLICATION_JSON)
	            .header("Autharization", "Bearer xyz")
	            .body(Mono.just(requestBody), Object.class)
	            .retrieve()
	            .bodyToMono(Object.class);
	    }
}
