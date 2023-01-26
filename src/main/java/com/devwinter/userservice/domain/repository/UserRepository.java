package com.devwinter.userservice.domain.repository;

import com.devwinter.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
