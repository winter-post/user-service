package com.devwinter.userservice.domain.service;

import com.devwinter.userservice.domain.entity.User;
import com.devwinter.userservice.domain.exception.UserException;
import com.devwinter.userservice.domain.repository.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.devwinter.userservice.domain.exception.UserErrorCode.EMAIL_DUPLICATE;

@Service
@RequiredArgsConstructor
public class UserCommandService {
    private final UserCommandRepository userCommandRepository;

    /**
     * 회원 계성 생성
     * 1. 이메일 중복 검사
     * 2. 회원 등록
     */
    public Long createMember(String email, String password) {
        if (userCommandRepository.existsByEmail(email)) {
            throw new UserException(EMAIL_DUPLICATE);
        }

        return userCommandRepository.save(createUserEntity(email, password)).getId();
    }

    private User createUserEntity(String email, String password) {
        return User.builder()
                   .email(email)
                   .password(password)
                   .build();
    }

}
