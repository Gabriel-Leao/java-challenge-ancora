package br.com.realtech.ancora.services;

import br.com.realtech.ancora.dtos.user.DeleteUserDto;
import br.com.realtech.ancora.dtos.user.UserRequestDto;
import br.com.realtech.ancora.dtos.user.UserResponseDto;
import br.com.realtech.ancora.entities.User;
import br.com.realtech.ancora.exceptions.ConflictException;
import br.com.realtech.ancora.exceptions.NotFoundException;
import br.com.realtech.ancora.exceptions.UnauthorizedException;
import br.com.realtech.ancora.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers_ShouldReturnListOfUsers() {
        User user1 = new User(1L, "User 1", "user1@test.com", "encoded1");
        User user2 = new User(2L, "User 2", "user2@test.com", "encoded2");
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.getUsers()).thenReturn(users);

        List<UserResponseDto> result = userService.getUsers();

        assertEquals(2, result.size());
        assertEquals("User 1", result.get(0).getName());
        assertEquals("user2@test.com", result.get(1).getEmail());
        verify(userRepository, times(1)).getUsers();
    }

    @Test
    void getUserById_ShouldReturnUser() {
        Long userId = 1L;
        User user = new User(userId, "Test User", "test@test.com", "encoded");

        when(userRepository.findUserById(userId)).thenReturn(user);

        UserResponseDto result = userService.getUserById(userId);

        assertEquals(userId, result.getId());
        assertEquals("Test User", result.getName());
        verify(userRepository, times(1)).findUserById(userId);
    }

    @Test
    void getUserById_WhenUserNotFound_ShouldThrowNotFoundException() {
        Long userId = 99L;

        when(userRepository.findUserById(userId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findUserById(userId);
    }

    @Test
    void createUser_ShouldCreateAndReturnUser() {
        UserRequestDto requestDto = new UserRequestDto("New User", "new@test.com", "password123");
        User savedUser = new User(1L, "New User", "new@test.com", "encoded");

        when(userRepository.findUserByEmail("new@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encoded");
        when(userRepository.createUser(requestDto)).thenReturn(savedUser);

        UserResponseDto result = userService.createUser(requestDto);

        assertEquals(1L, result.getId());
        assertEquals("New User", result.getName());
        verify(userRepository, times(1)).findUserByEmail("new@test.com");
        verify(userRepository, times(1)).createUser(requestDto);
    }

    @Test
    void createUser_WhenEmailExists_ShouldThrowConflictException() {
        UserRequestDto requestDto = new UserRequestDto("New User", "existing@test.com", "password123");
        User existingUser = new User(1L, "Existing User", "existing@test.com", "encoded");

        when(userRepository.findUserByEmail("existing@test.com")).thenReturn(Optional.of(existingUser));

        assertThrows(ConflictException.class, () -> userService.createUser(requestDto));
        verify(userRepository, times(1)).findUserByEmail("existing@test.com");
        verify(userRepository, never()).createUser(any());
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUser() {
        Long userId = 1L;
        UserRequestDto requestDto = new UserRequestDto("Updated User", "updated@test.com", "newpassword");
        User existingUser = new User(userId, "Old User", "old@test.com", "encoded");
        User updatedUser = new User(userId, "Updated User", "updated@test.com", "newencoded");

        when(userRepository.findUserById(userId)).thenReturn(existingUser);
        when(userRepository.findUserByEmail("updated@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("newpassword")).thenReturn("newencoded");
        when(userRepository.updateUser(userId, requestDto)).thenReturn(updatedUser);

        UserResponseDto result = userService.updateUser(userId, requestDto);

        assertEquals(userId, result.getId());
        assertEquals("Updated User", result.getName());
        verify(userRepository, times(1)).findUserById(userId);
        verify(userRepository, times(1)).findUserByEmail("updated@test.com");
        verify(userRepository, times(1)).updateUser(userId, requestDto);
    }

    @Test
    void updateUser_WhenEmailExistsForOtherUser_ShouldThrowConflictException() {
        Long userId = 1L;
        UserRequestDto requestDto = new UserRequestDto("Updated User", "existing@test.com", "newpassword");
        User existingUser = new User(userId, "Old User", "old@test.com", "encoded");
        User otherUser = new User(2L, "Other User", "existing@test.com", "encoded");

        when(userRepository.findUserById(userId)).thenReturn(existingUser);
        when(userRepository.findUserByEmail("existing@test.com")).thenReturn(Optional.of(otherUser));

        assertThrows(ConflictException.class, () -> userService.updateUser(userId, requestDto));
        verify(userRepository, times(1)).findUserById(userId);
        verify(userRepository, times(1)).findUserByEmail("existing@test.com");
        verify(userRepository, never()).updateUser(anyLong(), any());
    }

    @Test
    void deleteUser_ShouldDeleteWhenPasswordMatches() {
        Long userId = 1L;
        DeleteUserDto deleteDto = new DeleteUserDto();
        deleteDto.setPassword("correctpassword");
        User existingUser = new User(userId, "Test User", "test@test.com", "encoded");

        when(userRepository.findUserById(userId)).thenReturn(existingUser);
        when(passwordEncoder.matches("correctpassword", "encoded")).thenReturn(true);
        doNothing().when(userRepository).deleteUser(userId);

        userService.deleteUser(userId, deleteDto);

        verify(userRepository, times(1)).findUserById(userId);
        verify(passwordEncoder, times(1)).matches("correctpassword", "encoded");
        verify(userRepository, times(1)).deleteUser(userId);
    }

    @Test
    void deleteUser_WhenPasswordDoesNotMatch_ShouldThrowUnauthorizedException() {
        Long userId = 1L;
        DeleteUserDto deleteDto = new DeleteUserDto();
        deleteDto.setPassword("wrongpassword");
        User existingUser = new User(userId, "Test User", "test@test.com", "encoded");

        when(userRepository.findUserById(userId)).thenReturn(existingUser);
        when(passwordEncoder.matches("wrongpassword", "encoded")).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> userService.deleteUser(userId, deleteDto));
        verify(userRepository, times(1)).findUserById(userId);
        verify(passwordEncoder, times(1)).matches("wrongpassword", "encoded");
        verify(userRepository, never()).deleteUser(anyLong());
    }
}
