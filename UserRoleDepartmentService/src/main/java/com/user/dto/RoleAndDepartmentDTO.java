package com.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleAndDepartmentDTO {

	private int roleId;
    private String roleName;
    private int departmentId;
    private String departmentName;

    
}
