package com.codecool.elproyectegrande1.controller;

import com.codecool.elproyectegrande1.config.spring.security.WebSecurityConfig;
import com.codecool.elproyectegrande1.dto.dream.DreamDto;
import com.codecool.elproyectegrande1.dto.dream.NewDreamDto;
import com.codecool.elproyectegrande1.jwt.AuthEntryPointJwt;
import com.codecool.elproyectegrande1.jwt.JwtUtils;
import com.codecool.elproyectegrande1.service.CommentService;
import com.codecool.elproyectegrande1.service.DreamService;
import com.codecool.elproyectegrande1.service.ImageService;
import com.codecool.elproyectegrande1.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DreamController.class)
@Import(WebSecurityConfig.class)
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
    JwtUtils jwtUtils;


    @Autowired
    MockMvc mockMvc;

//    @Test
//    @WithMockUser
//    void shouldReturnDreamJson() throws Exception {
//
//        //given:
//        DreamDto dreamDto = new DreamDto(10L, "test", "test", new ArrayList<>(), 1L, new HashSet<>(), 0, 0);
//
//        NewDreamDto newDreamDto = new NewDreamDto("test", "test", new ArrayList<>(), "image.jpg");
//
//        Mockito.when(dreamService.addDream("10L", newDreamDto)).thenReturn((dreamDto));
//
//        //when:
//        ResultActions response = mockMvc.perform(post("/api/v1/dreams/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("""
//                   {
//                     "dreamTitle": "test",
//                     "dreamDescription": "test",
//                     "hashtags": [],
//                     "comments": [],
//                     "imageName": "image.jpg"
//                   }
//                """));
//
//        //then:
//        response.andExpect((status().isOk()));
//
//    }

}