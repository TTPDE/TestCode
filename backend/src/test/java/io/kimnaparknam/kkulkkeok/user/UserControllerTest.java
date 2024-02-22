package io.kimnaparknam.kkulkkeok.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kimnaparknam.kkulkkeok.config.WebSecurityConfig;
import io.kimnaparknam.kkulkkeok.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {UserController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class UserControllerTest {
    private MockMvc mockMvc;
    private Principal mockPrincipal;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    UserService userService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFIlter()))
                .build();
    }

    private void mockUserSetup() {
        String username = "Jin";
        String password = passwordEncoder.encode("jin123");
        String gender = "male";
        String introduction = "spartajin";
        String email = "jin@email.com";
        String nickname = "jin";
        User testUser = new User(username, password, gender, introduction, email, nickname);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

    @Test
    @DisplayName("회원 가입 요청 처리")
    void test1() throws Exception {
        //given
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("Bum");
        signupRequestDto.setPassword("1234");
        signupRequestDto.setGender("male");
        signupRequestDto.setEmail("Bum@email.com");
        signupRequestDto.setNickname("bum");

        //when-then
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequestDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @Test
    @DisplayName("회원 정보 수정")
    void test2() throws Exception {
        //given
        this.mockUserSetup();
        String testPassword = passwordEncoder.encode("passwrod");
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setIntroduction("자기소개");
        userRequestDto.setPassword(testPassword);

        //when-then
        mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto))
                .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}