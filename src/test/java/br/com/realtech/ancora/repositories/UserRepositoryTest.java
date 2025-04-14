package br.com.realtech.ancora.repositories;

import br.com.realtech.ancora.dtos.user.UserRequestDto;
import br.com.realtech.ancora.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void cleanDatabase() {
        userRepository.getUsers().forEach(user ->
                userRepository.deleteUser(user.getId()));
    }

    @Test
    void createUser_ShouldCreateAndReturnUserWithId() {
        UserRequestDto requestDto = new UserRequestDto("Test User", "test@test.com", "password123");

        User createdUser = userRepository.createUser(requestDto);

        assertNotNull(createdUser.getId());
        assertEquals("Test User", createdUser.getName());
        assertEquals("test@test.com", createdUser.getEmail());
        assertTrue(passwordEncoder.matches("password123", createdUser.getPassword()));
    }

    @Test
    void getUsers_ShouldReturnAllUsers() {
        userRepository.createUser(new UserRequestDto("User 1", "user1@test.com", "pass1"));
        userRepository.createUser(new UserRequestDto("User 2", "user2@test.com", "pass2"));

        List<User> users = userRepository.getUsers();

        assertEquals(2, users.size());
        assertEquals("User 1", users.get(0).getName());
        assertEquals("user2@test.com", users.get(1).getEmail());
    }

    @Test
    void findUserById_ShouldReturnUser() {
        User createdUser = userRepository.createUser(
                new UserRequestDto("Test User", "test@test.com", "password123"));
        Long userId = createdUser.getId();

        User foundUser = userRepository.findUserById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals("Test User", foundUser.getName());
    }

    @Test
    void findUserById_WhenUserNotFound_ShouldReturnNull() {
        User foundUser = userRepository.findUserById(999L);

        assertNull(foundUser);
    }

    @Test
    void findUserByEmail_ShouldReturnUser() {
        userRepository.createUser(
                new UserRequestDto("Test User", "test@test.com", "password123"));

        Optional<User> foundUser = userRepository.findUserByEmail("test@test.com");

        assertTrue(foundUser.isPresent());
        assertEquals("Test User", foundUser.get().getName());
    }

    @Test
    void findUserByEmail_WhenUserNotFound_ShouldReturnEmpty() {
        Optional<User> foundUser = userRepository.findUserByEmail("nonexistent@test.com");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void updateUser_ShouldUpdateUserDetails() {
        User createdUser = userRepository.createUser(
                new UserRequestDto("Old Name", "old@test.com", "oldpass"));
        Long userId = createdUser.getId();

        UserRequestDto updateDto = new UserRequestDto("New Name", "new@test.com", "newpass");

        User updatedUser = userRepository.updateUser(userId, updateDto);

        assertEquals(userId, updatedUser.getId());
        assertEquals("New Name", updatedUser.getName());
        assertEquals("new@test.com", updatedUser.getEmail());
        assertTrue(passwordEncoder.matches("newpass", updatedUser.getPassword()));
    }

    @Test
    void deleteUser_ShouldRemoveUser() {
        User createdUser = userRepository.createUser(
                new UserRequestDto("Test User", "test@test.com", "password123"));
        Long userId = createdUser.getId();

        userRepository.deleteUser(userId);

        User foundUser = userRepository.findUserById(userId);
        assertNull(foundUser);
    }

    @Test
    void whenEmailExists_ShouldReturnUserWithEmail() {
        userRepository.createUser(new UserRequestDto("User 1", "existing@test.com", "pass1"));

        Optional<User> result = userRepository.findUserByEmail("existing@test.com");

        assertTrue(result.isPresent());
        assertEquals("existing@test.com", result.get().getEmail());
    }

    @Test
    void whenEmailDoesNotExist_ShouldReturnEmpty() {
        Optional<User> result = userRepository.findUserByEmail("nonexistent@test.com");

        assertFalse(result.isPresent());
    }
}
