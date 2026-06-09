package com.bandfeed.user_service.domain.repository;

import com.bandfeed.user_service.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    User save(User user);
    void delete(User user);
    boolean existsByEmail(String email);
}
