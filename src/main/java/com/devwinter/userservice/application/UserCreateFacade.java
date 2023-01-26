package com.devwinter.userservice.application;

import com.devwinter.userservice.domain.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreateFacade {

    private final UserCommandService userCommandService;

    public Long createMember(UserCreateCommand command) {
        return userCommandService.createMember(command.email, command.pass);
    }

    public record UserCreateCommand(
            String email,
            String pass
    ) {

    }
}
