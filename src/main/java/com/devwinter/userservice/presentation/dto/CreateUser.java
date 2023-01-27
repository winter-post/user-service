package com.devwinter.userservice.presentation.dto;

import com.devwinter.userservice.application.UserCreateFacade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateUser {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        // TODO: Custom Email Valid Feature
        @Email(message = "이메일 형식에 맞지 않습니다.")
        @Size(max = 20, message = "이메일은 최대 20글자까지 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호는 필수 값 입니다.")
        private String password;

        public UserCreateFacade.UserCreateCommand convert() {
            return new UserCreateFacade.UserCreateCommand(email, password);
        }
    }

    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class Response {
        private Long memberId;

        public static BaseResponse<CreateUser.Response> success(Long memberId) {
            return BaseResponse.<CreateUser.Response>builder()
                               .code(HttpStatus.OK.toString())
                               .message("회원 등록에 성공하였습니다.")
                               .body(new CreateUser.Response(memberId))
                               .build();
        }
    }
}
