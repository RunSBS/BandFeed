package com.bandfeed.user_service.infrastructure.persistence;

import com.bandfeed.user_service.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    List<UserEntity> findByNicknameContainingIgnoreCase(String nickname);
    List<UserEntity> findByIdIn(List<UUID> ids);
}
