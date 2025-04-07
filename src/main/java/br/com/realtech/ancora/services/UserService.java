package br.com.realtech.ancora.services;

import br.com.realtech.ancora.dtos.user.UserRequestDto;
import br.com.realtech.ancora.dtos.user.UserResponseDto;
import br.com.realtech.ancora.entities.User;
import br.com.realtech.ancora.exceptions.ConflictException;
import br.com.realtech.ancora.exceptions.NotFoundException;
import br.com.realtech.ancora.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.getUsers();
        return users.stream()
                .map(UserResponseDto::new)
                .toList();
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }

        return new UserResponseDto(user);
    }

    public UserResponseDto createUser(UserRequestDto user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ConflictException("User with email already exists");
        }
        User createdUser = userRepository.createUser(user);
        return new UserResponseDto(createdUser);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto user) {
        userRepository.findUserById(id);
        Optional<User> userWithEmail = userRepository.findUserByEmail(user.getEmail());

        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
            throw new ConflictException("User with email already exists");
        }

        User updatedUser = userRepository.updateUser(id, user);
        return new UserResponseDto(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.findUserById(id);
        userRepository.deleteUser(id);
    }
}
