package br.com.realtech.ancora.controllers;

import br.com.realtech.ancora.dtos.user.CreateUserRequestDto;
import br.com.realtech.ancora.dtos.user.DeleteUserDto;
import br.com.realtech.ancora.dtos.user.PartialUpdateUserRequest;
import br.com.realtech.ancora.dtos.user.UserResponseDto;
import br.com.realtech.ancora.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        UserResponseDto user = new UserResponseDto(userService.getUserById(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto user) {
        UserResponseDto createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID id, @Valid @RequestBody CreateUserRequestDto user) {
        UserResponseDto updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> partialUpdateUser(@PathVariable UUID id, @Valid @RequestBody PartialUpdateUserRequest userRequest) {
        UserResponseDto updatedUser = userService.partialUpdateUser(id, userRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUSer(@PathVariable UUID id, @Valid @RequestBody DeleteUserDto user) {
        userService.deleteUser(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
