package com.user.config;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.dto.RoleResponseDTO;

@FeignClient(name = "role-service", url = "http://localhost:8088")  // URL to the same service
public interface RoleServiceClient {

	@GetMapping("/roles/{roleId}")
    RoleResponseDTO getRole(@PathVariable("roleId") int roleId);

    @PostMapping("/roles")
    RoleResponseDTO createRole(@RequestBody RoleResponseDTO role);

    @PutMapping("/roles/{roleId}")
    RoleResponseDTO updateRole(@PathVariable("roleId") int roleId, @RequestBody RoleResponseDTO role);

    @DeleteMapping("/roles/{roleId}")
    void deleteRole(@PathVariable("roleId") int roleId);
    
    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles();
    
}