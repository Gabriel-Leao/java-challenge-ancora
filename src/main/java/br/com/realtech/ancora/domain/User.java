package br.com.realtech.ancora.domain;

import br.com.realtech.ancora.application.utils.DateFormatterUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {}

    public User(String id, String name, String email, String password, String role, String BirthDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        setRole(role);
        setBirthDate(BirthDate);
    }

    public void setRole(String role) {
        this.role = role != null ? Role.valueOf(role.toUpperCase()) : Role.USER;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = DateFormatterUtil.transformStringToLocalDate(birthDate);
    }
}
