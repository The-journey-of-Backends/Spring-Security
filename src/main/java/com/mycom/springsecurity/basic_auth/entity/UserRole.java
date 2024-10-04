package com.mycom.springsecurity.basic_auth.entity;

public enum UserRole {
    ROLE_USER, ROLE_ADMIN;

    public String getRole() {
        return name();
    }
}
