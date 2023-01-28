package com.devwinter.userservice.application;

import com.devwinter.userservice.domain.dto.UserInfoDto;
import com.devwinter.userservice.domain.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserInfoFacade {
    private final UserQueryService userQueryService;

    public UserInfoDto getUserInfo(Long userId) {
        return userQueryService.getUser(userId);
    }
}
