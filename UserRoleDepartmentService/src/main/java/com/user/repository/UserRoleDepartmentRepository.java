package com.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.UserRoleDepartment;
import com.user.entity.UserRoleDepartmentKey;

@Repository
public interface UserRoleDepartmentRepository extends JpaRepository<UserRoleDepartment, UserRoleDepartmentKey> {
    List<UserRoleDepartment> findById_UserId(String userId);
    List<UserRoleDepartment> findById_UserIdAndId_RoleIdAndId_DepartmentId(String userId, Long roleId, Long departmentId);
    void deleteById_UserId(String userId);
}
