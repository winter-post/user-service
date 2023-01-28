package com.devwinter.userservice.domain.service;

import com.devwinter.userservice.domain.dto.UserInfoDto;
import com.devwinter.userservice.domain.entity.User;
import com.devwinter.userservice.domain.exception.UserException;
import com.devwinter.userservice.domain.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.devwinter.userservice.domain.exception.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserQueryRepository userQueryRepository;

    public UserInfoDto getUser(Long userId) {
        User user = userQueryRepository.findById(userId)
                                       .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        return UserInfoDto.of(user);
    }
}
