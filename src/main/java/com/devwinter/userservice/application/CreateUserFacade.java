package com.devwinter.userservice.application;

import com.devwinter.userservice.domain.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserFacade {

    private final UserCommandService userCommandService;
    private final PasswordEncoder passwordEncoder;

    public Long createMember(UserCreateCommand command) {
        String encryptPassword = passwordEncoder.encode(command.pass);
        return userCommandService.createMember(command.email, encryptPassword);
    }

    public record UserCreateCommand(
            String email,
            String pass
    ) {

    }
}
