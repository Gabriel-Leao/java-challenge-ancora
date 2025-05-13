package br.com.realtech.ancora.dtos.user.response;

import br.com.realtech.ancora.entities.User;
import lombok.Getter;

@Getter
public class CreateUserResponse extends UserResponse {
    private final String token;

    public CreateUserResponse(User user, String token) {
        super(user);
        this.token = token;
    }
}
