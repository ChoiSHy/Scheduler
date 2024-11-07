package com.scheduler.scheduler.infrastructure.repository;

import com.scheduler.scheduler.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    User getById(Long id);

    User getByUid(String id);
}
