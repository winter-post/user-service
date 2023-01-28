package com.devwinter.userservice.presentation.dto;

import com.devwinter.userservice.domain.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class UserInfo {
    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class Response {

        private Long userId;
        private String email;

        public static BaseResponse<UserInfo.Response> success(UserInfoDto userInfoDto) {
            return BaseResponse.<UserInfo.Response>builder()
                               .code(HttpStatus.OK.toString())
                               .message("회원 조회에 성공하였습니다.")
                               .body(new UserInfo.Response(userInfoDto.id(), userInfoDto.email()))
                               .build();
        }
    }
}
