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

import com.user.dto.DepartmentResponseDTO;

@FeignClient(name = "department-service", url = "http://localhost:8088")  // URL to the same service
public interface DepartmentServiceClient {

	@GetMapping("/departments/{departmentId}")
    DepartmentResponseDTO getDepartment(@PathVariable("departmentId") int departmentId);

    @PostMapping("/departments")
    DepartmentResponseDTO createDepartment(@RequestBody DepartmentResponseDTO department);

    @PutMapping("/departments/{departmentId}")
    DepartmentResponseDTO updateDepartment(@PathVariable("departmentId") int departmentId, @RequestBody DepartmentResponseDTO department);

    @DeleteMapping("/departments/{departmentId}")
    void deleteDepartment(@PathVariable("departmentId") int departmentId);
    
    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments();
}
