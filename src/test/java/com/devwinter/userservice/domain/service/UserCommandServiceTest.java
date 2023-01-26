package com.devwinter.userservice.domain.service;

import com.devwinter.userservice.domain.entity.User;
import com.devwinter.userservice.domain.exception.UserErrorCode;
import com.devwinter.userservice.domain.exception.UserException;
import com.devwinter.userservice.domain.repository.UserCommandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.devwinter.userservice.domain.exception.UserErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

    @Mock
    private UserCommandRepository userCommandRepository;

    @InjectMocks
    private UserCommandService userCommandService;

    @Test
    @DisplayName("회원 생성 시 이메일이 중복된 경우 테스트")
    void userCreateEmailDuplicateTest() {
        // given
        String email = "test@gmail.com";
        String pass = "test";
        given(userCommandRepository.existsByEmail(anyString()))
                .willReturn(true);

        // when
        UserException exception = assertThrows(UserException.class,
                () -> userCommandService.createMember(email, pass));

        // then
        verify(userCommandRepository, times(1)).existsByEmail(anyString());
        assertThat(exception.getUserErrorCode().getHttpStatus()).isEqualTo(EMAIL_DUPLICATE.getHttpStatus());
        assertThat(exception.getUserErrorCode().getMessage()).isEqualTo(EMAIL_DUPLICATE.getMessage());
    }

    @Test
    @DisplayName("회원 생성 테스트")
    void userCreateTest() {
        // given
        String email = "test@gmail.com";
        String pass = "test";
        given(userCommandRepository.existsByEmail(anyString()))
                .willReturn(false);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // when
        userCommandService.createMember(email, pass);

        // then
        verify(userCommandRepository, times(1)).existsByEmail(anyString());
        verify(userCommandRepository, times(1)).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getEmail()).isEqualTo(email);
        assertThat(userCaptor.getValue().getPassword()).isEqualTo(pass);
    }

}