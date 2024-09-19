package com.fredo.LMS.software.repository;

import com.fredo.LMS.software.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);

    Optional<User> findById(Long userId);
}
