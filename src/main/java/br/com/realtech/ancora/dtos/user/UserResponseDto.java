package br.com.realtech.ancora.dtos.user;

import br.com.realtech.ancora.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private UUID id;
    private String name;
    private String email;
    private LocalDate birthdate;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.birthdate = user.getBirthDate();
    }
}
