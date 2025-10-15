package com.example.smarthomeapi.repository;

import com.example.smarthomeapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Kullanıcı adına göre bir kullanıcıyı bulmak için bu metoda ihtiyacımız olacak.
    // Spring Data JPA, isminden ne yapması gerektiğini anlar.
    Optional<User> findByUsername(String username);
}