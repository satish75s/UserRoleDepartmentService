package com.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDepartmentResponseDTO {
	 private String userId;
	 private List<RoleAndDepartmentDTO> rolesAndDepartments;

    
}
