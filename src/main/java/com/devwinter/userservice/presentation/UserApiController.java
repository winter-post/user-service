package com.devwinter.userservice.presentation;

import com.devwinter.userservice.application.UserCreateFacade;
import com.devwinter.userservice.presentation.dto.BaseResponse;
import com.devwinter.userservice.presentation.dto.CreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {

    private final UserCreateFacade userCreateFacade;

    @PostMapping
    public BaseResponse<CreateUser.Response> createUser(@Valid @RequestBody CreateUser.Request request) {
        Long memberId = userCreateFacade.createMember(request.convert());
        return CreateUser.Response.success(memberId);
    }
}
