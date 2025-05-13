package br.com.realtech.ancora.dtos.user.response;

import br.com.realtech.ancora.entities.User;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class UserResponse {
    private final UUID id;
    private final String name;
    private final String email;
    private final LocalDate birthdate;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.birthdate = user.getBirthDate();
    }
}
