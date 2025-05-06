package br.com.realtech.ancora.services;

import br.com.realtech.ancora.dtos.user.DeleteUserDto;
import br.com.realtech.ancora.dtos.user.UserRequestDto;
import br.com.realtech.ancora.dtos.user.UserResponseDto;
import br.com.realtech.ancora.entities.User;
import br.com.realtech.ancora.exceptions.ConflictException;
import br.com.realtech.ancora.exceptions.NotFoundException;
import br.com.realtech.ancora.exceptions.UnauthorizedException;
import br.com.realtech.ancora.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(String id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDto::new)
                .toList();
    }

    public UserResponseDto createUser(UserRequestDto user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ConflictException("User with email already exists");
        }
        User newUser = new User(user);
        return new UserResponseDto(userRepository.save(newUser));
    }

    public UserResponseDto updateUser(String id, UserRequestDto user) {
        User existingUser = getUserById(id);
        Optional<User> userWithEmail = userRepository.findUserByEmail(user.getEmail());
        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
            throw new ConflictException("User with email already exists");
        }

        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setName(user.getName());
        existingUser.setBirthDate(user.getBirthdate());
        existingUser.setRole(user.getRole());
        existingUser.setEmail(user.getEmail());

        return new UserResponseDto(userRepository.save(existingUser));
    }

    public void deleteUser(String id, DeleteUserDto user) {
        User existingUser = getUserById(id);

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new UnauthorizedException("Wrong password");
        }

        userRepository.delete(existingUser);
    }
}
