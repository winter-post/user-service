package com.devwinter.userservice.presentation;

import com.devwinter.userservice.application.CreateUserFacade;
import com.devwinter.userservice.application.GetUserInfoFacade;
import com.devwinter.userservice.domain.dto.UserInfoDto;
import com.devwinter.userservice.presentation.dto.BaseResponse;
import com.devwinter.userservice.presentation.dto.CreateUser;
import com.devwinter.userservice.presentation.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {

    private final CreateUserFacade createUserFacade;
    private final GetUserInfoFacade getUserInfoFacade;

    @PostMapping
    public BaseResponse<CreateUser.Response> createUser(@Valid @RequestBody CreateUser.Request request) {
        Long memberId = createUserFacade.createMember(request.convert());
        return CreateUser.Response.success(memberId);
    }

    @GetMapping("/{userId}")
    public BaseResponse<UserInfo.Response> getUserInfo(@PathVariable Long userId) {
        UserInfoDto userInfo = getUserInfoFacade.getUserInfo(userId);
        return UserInfo.Response.success(userInfo);
    }

}
