package br.com.realtech.ancora.repositories;

import br.com.realtech.ancora.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserById(String id);
    Optional<User> findUserByEmail(String email);
}
