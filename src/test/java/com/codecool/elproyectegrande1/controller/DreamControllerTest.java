package com.codecool.elproyectegrande1.controller;

import com.codecool.elproyectegrande1.dto.dream.DreamDto;
import com.codecool.elproyectegrande1.dto.dream.NewDreamDto;
import com.codecool.elproyectegrande1.jwt.AuthEntryPointJwt;
import com.codecool.elproyectegrande1.jwt.JwtUtils;
import com.codecool.elproyectegrande1.security.oauth2.TokenProvider;
import com.codecool.elproyectegrande1.security.oauth2.authentication.CustomOauth2UserService;
import com.codecool.elproyectegrande1.security.oauth2.authentication.OAuth2AuthenticationFailureHandler;
import com.codecool.elproyectegrande1.security.oauth2.authentication.OAuth2AuthenticationSuccessHandler;
import com.codecool.elproyectegrande1.service.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = DreamController.class)
@AutoConfigureMockMvc
class DreamControllerTest {

    @MockBean
    DreamService dreamService;

    @MockBean
    ImageService imageService;

    @MockBean
    CommentService commentService;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    TokenProvider tokenProvider;

    @MockBean
    UserService userService;

    @MockBean
    CustomOauth2UserService customOauth2UserService;

    @MockBean
    OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @MockBean
    OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @MockBean
    JwtUtils jwtUtils;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser("spring")
    @Test
    public void shouldReturnDreamJson() throws Exception {

        //given:
        DreamDto dreamDto = new DreamDto(10L, "test", "test", new ArrayList<>(), 1L, new HashSet<>(), 0, 0,1L);

        NewDreamDto newDreamDto = new NewDreamDto("test", "test", new ArrayList<>(), "image.jpg", 1L);

        Mockito.when(dreamService.addDream("10L", newDreamDto)).thenReturn((dreamDto));

        //when:
        ResultActions response = mockMvc.perform(post("/api/v1/dreams/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNjkxMDEwMDI0LCJleHAiOjE2OTEwOTY0MjR9.OY1LVU-CAUz9aKmDEQklUy1hBWBDXl1aWZRv_3IdvBCWLe9_FjZp4YLcp6Mtr_aF0Po8FP2eceiSRxZVNrR08w", "")
                .content("""
                   {
                     "dreamTitle": "test",
                     "dreamDescription": "test",
                     "hashtags": [],
                     "comments": [],
                     "imageName": "image.jpg"
                   }
                """));

        //then:
        response.andExpect((status().isOk()));
    }

}