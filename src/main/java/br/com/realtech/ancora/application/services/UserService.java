package br.com.realtech.ancora.application.services;

import br.com.realtech.ancora.adapters.outbound.entities.JpaUserEntity;
import br.com.realtech.ancora.adapters.outbound.repositories.JpaUserRepository;
import br.com.realtech.ancora.application.dtos.user.DeleteUserDto;
import br.com.realtech.ancora.application.dtos.user.UserRequestDto;
import br.com.realtech.ancora.application.dtos.user.UserResponseDto;
import br.com.realtech.ancora.domain.exceptions.ConflictException;
import br.com.realtech.ancora.domain.exceptions.NotFoundException;
import br.com.realtech.ancora.domain.exceptions.UnauthorizedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(JpaUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public JpaUserEntity getUserById(String id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    public List<UserResponseDto> getUsers() {
        List<JpaUserEntity> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDto::new)
                .toList();
    }

    public UserResponseDto createUser(UserRequestDto user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ConflictException("User with email already exists");
        }
        JpaUserEntity newUser = new JpaUserEntity(user);
        return new UserResponseDto(userRepository.save(newUser));
    }

    public UserResponseDto updateUser(String id, UserRequestDto user) {
        JpaUserEntity existingUser = getUserById(id);
        Optional<JpaUserEntity> userWithEmail = userRepository.findUserByEmail(user.getEmail());
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
        JpaUserEntity existingUser = getUserById(id);

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new UnauthorizedException("Wrong password");
        }

        userRepository.delete(existingUser);
    }
}
