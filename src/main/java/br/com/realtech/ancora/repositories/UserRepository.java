package br.com.realtech.ancora.repositories;

import br.com.realtech.ancora.dtos.user.UserRequestDto;
import br.com.realtech.ancora.entities.User;
import br.com.realtech.ancora.exceptions.DatabaseException;
import br.com.realtech.ancora.factories.ConnectionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final ConnectionFactory connectionFactory;
    private final PasswordEncoder passwordEncoder;

    public UserRepository(ConnectionFactory connectionFactory, PasswordEncoder passwordEncoder) {
        this.connectionFactory = connectionFactory;
        this.passwordEncoder = passwordEncoder;

    }

    private User mapResultSetToUser(ResultSet rs) {
        try {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");

            return new User(id, name, email, password);
        } catch (SQLException e) {
            throw new DatabaseException("Error mapping result set to user", e);
        }
    }

    public List<User> getUsers() {
        String query = "SELECT id, name, email FROM users";

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery()
        ) {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving users", e);
        }
    }

    public User findUserById(Long id) {
        String query = "SELECT id, name, email, password FROM users WHERE id = ?";

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding user by ID", e);
        }

        return null;
    }

    public User createUser(UserRequestDto userDto) {
        String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = new User(userDto);

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
                user.setId(generatedKeys.getLong(1));
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Error creating user", e);
        }

        return user;
    }

    public Optional<User> findUserByEmail(String email) {
        String query = "SELECT id, name, email FROM users WHERE email = ?";

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding user by email", e);
        }

        return Optional.empty();
    }

    public User updateUser(Long id, UserRequestDto userDto) {
        String query = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, userDto.getName());
            statement.setString(2, userDto.getEmail());
            statement.setString(3, userDto.getPassword());
            statement.setLong(4, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating user", e);
        }

        return new User(id, userDto);
    }

    public void deleteUser(Long id) {
        String query = "DELETE FROM users WHERE id = ?";

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting user", e);
        }
    }
}
