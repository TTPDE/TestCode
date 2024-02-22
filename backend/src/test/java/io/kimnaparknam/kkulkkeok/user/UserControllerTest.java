package io.kimnaparknam.kkulkkeok.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kimnaparknam.kkulkkeok.config.WebSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

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
        private Principal principal;
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
}