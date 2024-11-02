package com.picpaysimplificado.repositories;

import com.picpaysimplificado.Domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findyUserByDocument(String document);

    Optional<User> findUserById(Long id);
}
