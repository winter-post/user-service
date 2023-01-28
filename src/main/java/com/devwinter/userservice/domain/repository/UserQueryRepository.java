package com.devwinter.userservice.domain.repository;

import com.devwinter.userservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQueryRepository extends JpaRepository<User, Long> {

}
