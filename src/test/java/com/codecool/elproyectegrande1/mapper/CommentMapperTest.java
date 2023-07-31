package com.codecool.elproyectegrande1.mapper;

import com.codecool.elproyectegrande1.dto.comment.CommentDto;
import com.codecool.elproyectegrande1.dto.comment.NewCommentDto;
import com.codecool.elproyectegrande1.entity.Comment;
import com.codecool.elproyectegrande1.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommentMapperTest {

    private final CommentMapper commentMapper = new CommentMapper();

    @Test
    void shouldMapEntityToCommentDto() {
        Comment comment = new Comment("This is a comment",5, "john_doe", new User());
        comment.setId(1L);

        CommentDto actual = commentMapper.mapEntityToCommentDto(comment);

        Assertions.assertEquals(comment.getId(), actual.getId());
        Assertions.assertEquals(comment.getComment(), actual.getComment());
        Assertions.assertEquals(comment.getUsername(), actual.getUsername());
    }

    @Test
    void shouldMapNewCommentDtoToEntity() {
        NewCommentDto commentDto = new NewCommentDto("This is comment test");

        Comment actual = commentMapper.mapNewCommentDtoToEntity(commentDto, "Test user", new User());

        Assertions.assertEquals(commentDto.getComment(), actual.getComment());
    }
}
