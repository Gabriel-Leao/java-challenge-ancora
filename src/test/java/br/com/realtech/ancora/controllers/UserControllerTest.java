package br.com.realtech.ancora.controllers;

import br.com.realtech.ancora.dtos.user.DeleteUserDto;
import br.com.realtech.ancora.dtos.user.UserRequestDto;
import br.com.realtech.ancora.dtos.user.UserResponseDto;
import br.com.realtech.ancora.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        UserResponseDto user1 = new UserResponseDto(1L, "User 1", "user1@test.com");
        UserResponseDto user2 = new UserResponseDto(2L, "User 2", "user2@test.com");
        List<UserResponseDto> users = Arrays.asList(user1, user2);

        when(userService.getUsers()).thenReturn(users);

        ResponseEntity<List<UserResponseDto>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).getUsers();
    }

    @Test
    void getUserById_ShouldReturnUser() {
        Long userId = 1L;
        UserResponseDto expectedUser = new UserResponseDto(userId, "Test User", "test@test.com");

        when(userService.getUserById(userId)).thenReturn(expectedUser);

        ResponseEntity<UserResponseDto> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        UserRequestDto requestDto = new UserRequestDto("New User", "new@test.com", "password123");
        UserResponseDto expectedResponse = new UserResponseDto(1L, "New User", "new@test.com");

        when(userService.createUser(requestDto)).thenReturn(expectedResponse);

        ResponseEntity<UserResponseDto> response = userController.createUser(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(userService, times(1)).createUser(requestDto);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        Long userId = 1L;
        UserRequestDto requestDto = new UserRequestDto("Updated User", "updated@test.com", "newpassword");
        UserResponseDto expectedResponse = new UserResponseDto(userId, "Updated User", "updated@test.com");

        when(userService.updateUser(userId, requestDto)).thenReturn(expectedResponse);

        ResponseEntity<UserResponseDto> response = userController.updateUser(userId, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(userService, times(1)).updateUser(userId, requestDto);
    }

    @Test
    void deleteUser_ShouldReturnNoContent() {
        Long userId = 1L;
        DeleteUserDto deleteDto = new DeleteUserDto();
        deleteDto.setPassword("password123");

        doNothing().when(userService).deleteUser(userId, deleteDto);

        ResponseEntity<Void> response = userController.deleteUSer(userId, deleteDto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId, deleteDto);
    }
}
