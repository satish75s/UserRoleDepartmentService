package com.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRoleDepartmentRequestDTO {
	 private String userId;  // User ID
	 private List<RoleAndDepartmentDTO> rolesAndDepartments;  // List of roles and departments

}
