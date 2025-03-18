package com.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.config.DepartmentServiceClient;
import com.user.config.RoleServiceClient;
import com.user.config.UserServiceClient;
import com.user.dto.DepartmentResponseDTO;
import com.user.dto.RoleAndDepartmentDTO;
import com.user.dto.RoleResponseDTO;
import com.user.dto.UserResponseDTO;
import com.user.dto.UserRoleDepartmentRequestDTO;
import com.user.dto.UserRoleDepartmentResponseDTO;
import com.user.entity.UserRoleDepartment;
import com.user.repository.UserRoleDepartmentRepository;

import jakarta.transaction.Transactional;

@Service
public class UserRoleDepartmentService {

    @Autowired
    private UserRoleDepartmentRepository userRoleDepartmentRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private RoleServiceClient roleServiceClient;

    @Autowired
    private DepartmentServiceClient departmentServiceClient;

    public UserRoleDepartmentResponseDTO createUserRoleDepartment(UserRoleDepartmentRequestDTO requestDTO) {
        List<UserRoleDepartment> userRoleDepartments = new ArrayList<>();
        
        // Fetch User details via Feign client
        UserResponseDTO userResponse = userServiceClient.getUser(requestDTO.getUserId());

        // Iterate over the rolesAndDepartments list
        for (RoleAndDepartmentDTO roleAndDepartmentDTO : requestDTO.getRolesAndDepartments()) {
            
            // Fetch Role details via Feign client
            RoleResponseDTO roleResponse = roleServiceClient.getRole(roleAndDepartmentDTO.getRoleId());

            // Fetch Department details via Feign client
            DepartmentResponseDTO departmentResponse = departmentServiceClient.getDepartment(roleAndDepartmentDTO.getDepartmentId());
            
            // Create a new UserRoleDepartment association (only roleId and departmentId)
            UserRoleDepartment userRoleDepartment = new UserRoleDepartment(
                userResponse.getUserId(),
                roleResponse.getRoleId(),
                departmentResponse.getDepartmentId()
            );

            // Add the new association to the list
            userRoleDepartments.add(userRoleDepartment);
        }

        // Save all the user-role-department associations in the database
        userRoleDepartmentRepository.saveAll(userRoleDepartments);

        // Map the saved associations to a response DTO
        List<RoleAndDepartmentDTO> rolesAndDepartments = userRoleDepartments.stream()
                .map(association -> {
                    // Fetch Role details via Feign client
                    RoleResponseDTO roleResponse = roleServiceClient.getRole(association.getId().getRoleId());

                    // Fetch Department details via Feign client
                    DepartmentResponseDTO departmentResponse = departmentServiceClient.getDepartment(association.getId().getDepartmentId());

                    // Return a new RoleAndDepartmentDTO with roleName and departmentName
                    return new RoleAndDepartmentDTO(
                            association.getId().getRoleId(),
                            roleResponse.getRoleName(),  // roleName
                            association.getId().getDepartmentId(),
                            departmentResponse.getDepartmentName()  // departmentName
                    );
                })
                .collect(Collectors.toList());

        // Return the response DTO with the userId and rolesAndDepartments
        return new UserRoleDepartmentResponseDTO(userResponse.getUserId(), rolesAndDepartments);
    }

    /**
     * Method to fetch user-role-department associations for a specific user.
     */
    public List<UserRoleDepartment> getUserRoleDepartments(String userId) {
        return userRoleDepartmentRepository.findById_UserId(userId);
    }

    /**
     * Method to update existing user-role-department associations.
     */
    @Transactional
    public UserRoleDepartmentResponseDTO updateUserRoleDepartment(String userId, UserRoleDepartmentRequestDTO requestDTO) {
        // First delete the existing associations
        userRoleDepartmentRepository.deleteById_UserId(userId);

        // Then create new associations based on the updated data
        return createUserRoleDepartment(requestDTO);
    }

    /**
     * Method to delete all user-role-department associations for a user.
     */
    public void deleteUserRoleDepartment(String userId) {
        userRoleDepartmentRepository.deleteById_UserId(userId);
    }
}
