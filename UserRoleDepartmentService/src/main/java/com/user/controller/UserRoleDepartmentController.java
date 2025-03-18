package com.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.config.DepartmentServiceClient;
import com.user.config.RoleServiceClient;
import com.user.dto.DepartmentResponseDTO;
import com.user.dto.RoleAndDepartmentDTO;
import com.user.dto.RoleResponseDTO;
import com.user.dto.UserRoleDepartmentRequestDTO;
import com.user.dto.UserRoleDepartmentResponseDTO;
import com.user.entity.UserRoleDepartment;
import com.user.service.UserRoleDepartmentService;

@RestController
@RequestMapping("/user-role-department")
public class UserRoleDepartmentController {

    @Autowired
    private UserRoleDepartmentService userRoleDepartmentService;
    
    @Autowired
    DepartmentServiceClient departmentServiceClient;
    
    @Autowired
    RoleServiceClient roleServiceClient;

    // CREATE: Add new associations
    @PostMapping
    public ResponseEntity<UserRoleDepartmentResponseDTO> createUserRoleDepartment(@RequestBody UserRoleDepartmentRequestDTO requestDTO) {
        UserRoleDepartmentResponseDTO responseDTO = userRoleDepartmentService.createUserRoleDepartment(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRoleDepartmentResponseDTO> getUserRoleDepartment(@PathVariable String userId) {
        List<UserRoleDepartment> associations = userRoleDepartmentService.getUserRoleDepartments(userId);       
        List<RoleAndDepartmentDTO> rolesAndDepartments = associations.stream()
                .map(association -> {                   
                    RoleResponseDTO roleResponse = roleServiceClient.getRole(association.getId().getRoleId());
                    DepartmentResponseDTO departmentResponse = departmentServiceClient.getDepartment(association.getId().getDepartmentId());

                    return new RoleAndDepartmentDTO(
                            association.getId().getRoleId(),
                            roleResponse.getRoleName(),  
                            association.getId().getDepartmentId(),
                            departmentResponse.getDepartmentName()  
                    );
                })
                .collect(Collectors.toList());

        // Create a UserRoleDepartmentResponseDTO with the grouped data
        UserRoleDepartmentResponseDTO responseDTO = new UserRoleDepartmentResponseDTO(userId, rolesAndDepartments);
        return ResponseEntity.ok(responseDTO);
    }
    
 

    // UPDATE: Update user-role-department associations
    @PutMapping("/{userId}")
    public ResponseEntity<UserRoleDepartmentResponseDTO> updateUserRoleDepartment(@PathVariable String userId, @RequestBody UserRoleDepartmentRequestDTO requestDTO) {
    	UserRoleDepartmentResponseDTO responseDTO = userRoleDepartmentService.updateUserRoleDepartment(userId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // DELETE: Delete user-role-department associations for a specific user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserRoleDepartment(@PathVariable String userId) {
        userRoleDepartmentService.deleteUserRoleDepartment(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
