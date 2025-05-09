package br.com.realtech.ancora.entities;

import br.com.realtech.ancora.dtos.user.CreateUserRequestDto;
import br.com.realtech.ancora.models.Role;
import br.com.realtech.ancora.utils.DateFormatterUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter
    private UUID id;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false, unique = true)
    @Setter
    private String email;

    @Column(nullable = false)
    @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public User(CreateUserRequestDto createUserDto) {
        this.name = createUserDto.getName();
        this.email = createUserDto.getEmail();
        this.password = createUserDto.getPassword();
        setBirthDate(createUserDto.getBirthdate());
        setRole(createUserDto.getRole());
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void setRole(String role) {
        this.role = role != null ? Role.valueOf(role.toUpperCase()) : Role.USER;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = DateFormatterUtil.transformStringToLocalDate(birthDate);
    }
}
