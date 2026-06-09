package com.bandfeed.user_service.infrastructure.persistence;

import com.bandfeed.user_service.domain.model.User;
import com.bandfeed.user_service.domain.repository.UserRepository;
import com.bandfeed.user_service.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public User save(User user) {
        return jpaUserRepository.save(UserEntity.from(user)).toDomain();
    }

    @Override
    public void delete(User user) {
        jpaUserRepository.deleteById(user.getId());
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}
