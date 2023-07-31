package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.mapper.CommentMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentMapper commentMapper;
    @InjectMocks
    private CommentService commentService;


}