package com.devwinter.userservice.domain.dto;


import com.devwinter.userservice.domain.entity.User;

public record UserInfoDto(
        Long id,
        String email
) {
    public static UserInfoDto of(User user) {
        return new UserInfoDto(user.getId(), user.getEmail());
    }
}
