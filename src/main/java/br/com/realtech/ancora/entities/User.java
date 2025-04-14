package br.com.realtech.ancora.entities;

import br.com.realtech.ancora.dtos.user.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
//@Entity(name = "users")
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

//    @Column(nullable = false)
    private String name;

//    @Column(nullable = false, unique = true)
    private String email;

//    @Column(nullable = false)
    @Setter
    private String password;

//    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

//    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//        updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }

    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(UserRequestDto createUserDto) {
        this.name = createUserDto.getName();
        this.email = createUserDto.getEmail();
        this.password = createUserDto.getPassword();
    }

    public User(Long id, UserRequestDto createUserDto) {
        this.id = id;
        this.name = createUserDto.getName();
        this.email = createUserDto.getEmail();
        this.password = createUserDto.getPassword();
    }
}
