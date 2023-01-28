package com.devwinter.userservice.domain.service;

import com.devwinter.userservice.domain.exception.UserException;
import com.devwinter.userservice.domain.repository.UserQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.devwinter.userservice.domain.exception.UserErrorCode.EMAIL_DUPLICATE;
import static com.devwinter.userservice.domain.exception.UserErrorCode.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceTest {

    @Mock
    private UserQueryRepository userQueryRepository;

    @InjectMocks
    private UserQueryService userQueryService;
    
    @Test
    @DisplayName("회원 조회 시 회원이 없는 경우 테스트")
    void getUserNotFoundTest() {
        // given
        given(userQueryRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when
        UserException userException =
                assertThrows(UserException.class, () -> userQueryService.getUser(1L));

        // then
        verify(userQueryRepository, times(1)).findById(anyLong());
        assertThat(userException.getUserErrorCode().getHttpStatus()).isEqualTo(USER_NOT_FOUND.getHttpStatus());
        assertThat(userException.getUserErrorCode().getMessage()).isEqualTo(USER_NOT_FOUND.getMessage());
    }
}