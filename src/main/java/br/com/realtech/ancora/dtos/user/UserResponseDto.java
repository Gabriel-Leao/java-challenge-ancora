package br.com.realtech.ancora.dtos.user;

import br.com.realtech.ancora.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String id;
    private String name;
    private String email;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
