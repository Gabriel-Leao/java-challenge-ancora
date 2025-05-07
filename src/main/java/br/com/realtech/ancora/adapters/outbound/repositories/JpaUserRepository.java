package br.com.realtech.ancora.adapters.outbound.repositories;

import br.com.realtech.ancora.adapters.outbound.entities.JpaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<JpaUserEntity, String> {
    Optional<JpaUserEntity> findUserById(String id);
    Optional<JpaUserEntity> findUserByEmail(String email);
}
