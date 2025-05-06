package br.com.realtech.ancora.controllers;

import br.com.realtech.ancora.dtos.user.DeleteUserDto;
import br.com.realtech.ancora.dtos.user.UserRequestDto;
import br.com.realtech.ancora.dtos.user.UserResponseDto;
import br.com.realtech.ancora.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String id) {
        UserResponseDto user = new UserResponseDto(userService.getUserById(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto user) {
        UserResponseDto createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable String id, @Valid @RequestBody UserRequestDto user) {
        UserResponseDto updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUSer(@PathVariable String id, @Valid @RequestBody DeleteUserDto user) {
        userService.deleteUser(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
