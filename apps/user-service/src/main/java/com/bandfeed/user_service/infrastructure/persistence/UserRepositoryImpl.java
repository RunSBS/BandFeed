package com.bandfeed.user_service.infrastructure.persistence;

import com.bandfeed.user_service.domain.model.User;
import com.bandfeed.user_service.domain.repository.UserRepository;
import com.bandfeed.user_service.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpa;

    @Override
    public Optional<User> findById(UUID id) {
        return jpa.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public User save(User user) {
        return jpa.saveAndFlush(UserEntity.from(user)).toDomain();
    }

    @Override
    public void delete(User user) {
        jpa.deleteById(user.getId());
        jpa.flush();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }

    @Override
    public List<User> findByNicknameContaining(String nickname) {
        return jpa.findByNicknameContainingIgnoreCase(nickname).stream()
                .map(UserEntity::toDomain)
                .toList();
    }

    @Override
    public List<User> findAllByIds(List<UUID> ids) {
        return jpa.findByIdIn(ids).stream()
                .map(UserEntity::toDomain)
                .toList();
    }
}
