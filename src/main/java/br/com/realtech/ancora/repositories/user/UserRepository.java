package br.com.realtech.ancora.repositories.user;

import br.com.realtech.ancora.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserById(UUID id);
    Optional<User> findUserByEmail(String email);
}
