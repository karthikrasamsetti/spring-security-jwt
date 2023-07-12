package com.spring.security.auth;

import com.spring.security.User.Role;

public record RegisterRequest(String firstname, String lastname, String email, String password, Role role) {
}
