package br.com.realtech.ancora.services;

import br.com.realtech.ancora.dtos.user.request.DeleteUserRequest;
import br.com.realtech.ancora.dtos.user.request.PartialUpdateUserRequest;
import br.com.realtech.ancora.dtos.user.request.UpsertUserRequest;
import br.com.realtech.ancora.dtos.user.response.CreateUserResponse;
import br.com.realtech.ancora.dtos.user.response.UserResponse;
import br.com.realtech.ancora.entities.User;
import br.com.realtech.ancora.exceptions.ConflictException;
import br.com.realtech.ancora.exceptions.NotFoundException;
import br.com.realtech.ancora.exceptions.UnauthorizedException;
import br.com.realtech.ancora.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User getUserById(UUID id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    private void updateIfPresent(String value, Consumer<String> setter) {
        if (value != null && !value.isBlank()) {
            setter.accept(value);
        }
    }

    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponse::new)
                .toList();
    }

    public CreateUserResponse createUser(UpsertUserRequest createUserRequest) {
        if (userRepository.findUserByEmail(createUserRequest.getEmail()).isPresent()) {
            throw new ConflictException("User with email already exists");
        }

        createUserRequest.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        User newUser = new User(createUserRequest);
        User createdUser = userRepository.save(newUser);
        String token = jwtService.generateToken(createdUser);

        return new CreateUserResponse(createdUser, token);
    }

    public UserResponse updateUser(UUID id, UpsertUserRequest updateUserRequest) {
        User existingUser = getUserById(id);
        Optional<User> userWithEmail = userRepository.findUserByEmail(updateUserRequest.getEmail());
        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
            throw new ConflictException("User with email already exists");
        }

        existingUser.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        existingUser.setName(updateUserRequest.getName());
        existingUser.setBirthDate(updateUserRequest.getBirthdate());
        existingUser.setRole(updateUserRequest.getRole());
        existingUser.setEmail(updateUserRequest.getEmail());

        return new UserResponse(userRepository.save(existingUser));
    }

    public UserResponse partialUpdateUser(UUID id, PartialUpdateUserRequest userData) {
        User existingUser = getUserById(id);

        updateIfPresent(userData.getName(), existingUser::setName);
        updateIfPresent(userData.getBirthdate(), existingUser::setBirthDate);
        updateIfPresent(userData.getRole(), existingUser::setRole);
        updateIfPresent(userData.getEmail(), existingUser::setEmail);

        if (userData.getPassword() != null && !userData.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(userData.getPassword()));
        }

        userRepository.save(existingUser);
        return new UserResponse(existingUser);
    }

    public void deleteUser(UUID id, DeleteUserRequest user) {
        User existingUser = getUserById(id);

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new UnauthorizedException("Wrong password");
        }

        userRepository.delete(existingUser);
    }
}
