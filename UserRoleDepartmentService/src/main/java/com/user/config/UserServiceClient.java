package com.user.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.dto.UserResponseDTO;

@FeignClient(name = "user-service", url = "http://localhost:8088")  // Change the URL to the actual user service URL
public interface UserServiceClient {

	@GetMapping("/users/{userId}")
    UserResponseDTO getUser(@PathVariable("userId") String userId);

    @PostMapping("/users")
    UserResponseDTO createUser(@RequestBody UserResponseDTO userDTO);

    @PutMapping("/users/{userId}")
    UserResponseDTO updateUser(@PathVariable("userId") String userId, @RequestBody UserResponseDTO userDTO);

    @DeleteMapping("/users/{userId}")
    void deleteUser(@PathVariable("userId") String userId);
    
    @GetMapping("/users/usersByPage")
	public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size);
	
}