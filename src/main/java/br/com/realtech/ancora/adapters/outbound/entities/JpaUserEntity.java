package br.com.realtech.ancora.adapters.outbound.entities;

import br.com.realtech.ancora.application.dtos.user.UserRequestDto;
import br.com.realtech.ancora.application.utils.DateFormatterUtil;
import br.com.realtech.ancora.domain.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class JpaUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter
    private String id;

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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public JpaUserEntity(UserRequestDto createUserDto) {
        this.name = createUserDto.getName();
        this.email = createUserDto.getEmail();
        this.password = createUserDto.getPassword();
        setBirthDate(createUserDto.getBirthdate());
        setRole(createUserDto.getRole());
    }

    public JpaUserEntity(String id, UserRequestDto createUserDto) {
        this.id = id;
        this.name = createUserDto.getName();
        this.email = createUserDto.getEmail();
        this.password = createUserDto.getPassword();
    }

    public void setRole(String role) {
        this.role = role != null ? Role.valueOf(role.toUpperCase()) : Role.USER;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = DateFormatterUtil.transformStringToLocalDate(birthDate);
    }
}
