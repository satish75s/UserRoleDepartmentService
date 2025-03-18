package com.user.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserRoleDepartmentKey implements Serializable {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "department_id")
    private Integer departmentId;

    // Default constructor
    public UserRoleDepartmentKey() {}

    // Constructor to initialize the composite key
    public UserRoleDepartmentKey(String userId, int roleId, int departmentId) {
        this.userId = userId;
        this.roleId = roleId;
        this.departmentId = departmentId;
    }

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    // Override equals and hashCode to ensure the composite key works properly in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleDepartmentKey that = (UserRoleDepartmentKey) o;
        return userId.equals(that.userId) &&
               roleId.equals(that.roleId) &&
               departmentId.equals(that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId, departmentId);
    }
}
