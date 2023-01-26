package com.devwinter.userservice.domain.repository;

import com.devwinter.userservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommandRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
