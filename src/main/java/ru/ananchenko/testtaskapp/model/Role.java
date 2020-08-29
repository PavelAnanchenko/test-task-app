package ru.ananchenko.testtaskapp.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    DEFAULT_USER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
