package com.devwinter.userservice.presentation;

import com.devwinter.userservice.application.CreateUserFacade;
import com.devwinter.userservice.application.GetUserInfoFacade;
import com.devwinter.userservice.domain.dto.UserInfoDto;
import com.devwinter.userservice.domain.entity.User;
import com.devwinter.userservice.presentation.dto.CreateUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.devwinter.userservice.presentation.exceptionhandler.GlobalExceptionHandler.ARGUMENT_NOT_VALID_STRING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    private CreateUserFacade createUserFacade;
    @MockBean
    private GetUserInfoFacade getUserInfoFacade;

    private static String USER_API_BASE_URL = "/api/v1/users";

    @ParameterizedTest
    @ValueSource(strings = {" ", "             "})
    @DisplayName("회원 가입 시 이메일 빈값인 경우 유효성 검사 테스트")
    void createUserEmailEmptyValidAPITest(String emptyEmailParam) throws Exception {
        // given
        String json = createUserJson(CreateUserMother.customEmail(emptyEmailParam)
                                                     .build());

        // when & then
        mockMvc.perform(post(USER_API_BASE_URL)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.toString()))
               .andExpect(jsonPath("$.message").value(ARGUMENT_NOT_VALID_STRING))
               .andExpect(jsonPath("$.body.email").value("이메일 형식에 맞지 않습니다."))
               .andDo(print());
    }

    @Test
    @DisplayName("회원 가입 시 이메일 길이가 최대 20글자가 넘은 경우 테스트")
    void createUserEmailMaxLengthOverAPITest() throws Exception {
        // given
        String emailMaxOverParam = "abcdejfkaisjkalsdlkfasd@gamil.com";
        String json = createUserJson(CreateUserMother.customEmail(emailMaxOverParam)
                                                     .build());

        // when & then
        mockMvc.perform(post(USER_API_BASE_URL)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.toString()))
               .andExpect(jsonPath("$.message").value(ARGUMENT_NOT_VALID_STRING))
               .andExpect(jsonPath("$.body.email[0]").value("이메일은 최대 20글자까지 입력해주세요."))
               .andDo(print());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "zxc asdfkl", "d89123j", "..", "!"})
    @DisplayName("회원 가입 시 이메일 형식이 맞지 않은 경우 테스트")
    void createUserEmailNotFormatAPITest(String emailNotFormatParam) throws Exception {
        // given
        String json = createUserJson(CreateUserMother.customEmail(emailNotFormatParam)
                                                     .build());

        // when & then
        mockMvc.perform(post(USER_API_BASE_URL)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.toString()))
               .andExpect(jsonPath("$.message").value(ARGUMENT_NOT_VALID_STRING))
               .andExpect(jsonPath("$.body.email[0]").value("이메일 형식에 맞지 않습니다."))
               .andDo(print());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "             "})
    @DisplayName("회원 가입 시 비밀번호가 빈값인 경우 유효성 검사 테스트")
    void createUserPasswordEmptyValidAPITest(String emptyPasswordParam) throws Exception {
        // given
        String json = createUserJson(CreateUserMother.customPassword(emptyPasswordParam)
                                                     .build());

        // when & then
        mockMvc.perform(post(USER_API_BASE_URL)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.toString()))
               .andExpect(jsonPath("$.message").value(ARGUMENT_NOT_VALID_STRING))
               .andExpect(jsonPath("$.body.password").value("비밀번호는 필수 값 입니다."))
               .andDo(print());
    }

    @Test
    @DisplayName("회원 가입 API 테스트")
    void createUserAPITest() throws Exception {
        // given
        CreateUser.Request request = CreateUserMother.complete()
                                                     .build();
        String json = objectMapper.writeValueAsString(request);

        given(createUserFacade.createMember(any()))
                .willReturn(1L);

        // when
        mockMvc.perform(post(USER_API_BASE_URL)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value(HttpStatus.OK.toString()))
               .andExpect(jsonPath("$.message").value("회원 등록에 성공하였습니다."))
               .andExpect(jsonPath("$.body.memberId").value(1))
               .andDo(print());

        // then
    }

    @Test
    @DisplayName("회원 조회 API 테스트")
    void getUserInfoAPITest() throws Exception {
        // given
        UserInfoDto userInfoDto = UserInfoDto.of(UserMother.complete()
                                                           .build());
        given(getUserInfoFacade.getUserInfo(anyLong()))
                .willReturn(userInfoDto);


        // when & then
        mockMvc.perform(get(USER_API_BASE_URL + "/1")
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value(HttpStatus.OK.toString()))
               .andExpect(jsonPath("$.message").value("회원 조회에 성공하였습니다."))
               .andExpect(jsonPath("$.body.email").value(UserMother.DEFAULT_TEST_EAMIL))
               .andDo(print());
    }

    private String createUserJson(CreateUser.Request request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

    static class UserMother {
        private static String DEFAULT_TEST_EAMIL = "test@gmail.com";
        private static String DEFAULT_TEST_PASSWORD = "test1234";

        public static User.UserBuilder complete() {
            return User.builder()
                       .email(DEFAULT_TEST_EAMIL)
                       .password(DEFAULT_TEST_PASSWORD);
        }
    }

    static class CreateUserMother {
        private static String DEFAULT_TEST_EAMIL = "test@gmail.com";
        private static String DEFAULT_TEST_PASSWORD = "test1234";

        public static CreateUser.Request.RequestBuilder customEmail(String email) {
            return internal(email, DEFAULT_TEST_PASSWORD);
        }

        public static CreateUser.Request.RequestBuilder customPassword(String password) {
            return internal(DEFAULT_TEST_EAMIL, password);
        }

        public static CreateUser.Request.RequestBuilder custom(String email, String password) {
            return internal(email, password);
        }

        public static CreateUser.Request.RequestBuilder complete() {
            return internal(DEFAULT_TEST_EAMIL, DEFAULT_TEST_PASSWORD);
        }

        private static CreateUser.Request.RequestBuilder internal(String email, String password) {
            return CreateUser.Request.builder()
                                     .email(email)
                                     .password(password);
        }
    }

}