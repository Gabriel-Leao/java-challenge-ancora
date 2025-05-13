package br.com.realtech.ancora.dtos.user.request;

import br.com.realtech.ancora.validations.birthdate.ValidBirthdate;
import br.com.realtech.ancora.validations.role.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertUserRequest {
    @NotBlank(message = "Name can't be empty")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email is not valid")
    @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
    private String email;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Setter
    private String password;

    @NotBlank(message = "Birthdate can't be empty")
    @ValidBirthdate
    private String birthdate;

    @ValidRole
    private String role;
}
