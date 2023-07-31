package com.codecool.elproyectegrande1.repository;

import com.codecool.elproyectegrande1.ElProyecteGrande1;
import com.codecool.elproyectegrande1.config.H2TestProfileJPAConfig;
import com.codecool.elproyectegrande1.entity.Comment;
import com.codecool.elproyectegrande1.entity.Dream;
import com.codecool.elproyectegrande1.entity.Dreamer;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ElProyecteGrande1.class, H2TestProfileJPAConfig.class })
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DreamRepository dreamRepository;

    @Test
    void givenCommentRepository_whenSaveAndRetrieveEntity_thenOK() {
        Comment newComment = Instancio.of(Comment.class).create();
        Comment comment = commentRepository.save(newComment);
        comment.setId(1L);
        Comment foundEntity = commentRepository.findById(comment.getId()).orElse(null);
        Assertions.assertNotNull(foundEntity);
        Assertions.assertEquals(comment.getCommentText(), foundEntity.getCommentText());
    }

    @Test
    void shouldLikeCommentWhenRetrievedFromRepository() {
        Comment newComment = Instancio.of(Comment.class).create();
        Comment comment = commentRepository.save(newComment);
        comment.setId(1L);
        Comment actual = commentRepository.findById(comment.getId()).orElse(null);
        Assertions.assertNotNull(actual);
        actual.setLikes(comment.getLikes() + 1);
        Assertions.assertEquals(actual.getLikes(), 1);
    }

    @Test
    void shouldUpdateCommentWhenRetrievedFromRepository() {
        Comment newComment = Instancio.of(Comment.class).create();
        Comment comment = commentRepository.save(newComment);
        comment.setId(1L);
        Comment actual = commentRepository.findById(comment.getId()).orElse(null);
        Assertions.assertNotNull(actual);
        LocalDateTime rightNow = LocalDateTime.now();
        String newDesc = "new testing description";
        actual.setComment(newDesc);
        actual.setTimeUpdated(rightNow);
        Assertions.assertEquals(actual.getComment(), newDesc);
        Assertions.assertEquals(actual.getTimeUpdated(), rightNow);
    }

    @Test
    void shouldDeleteComment() {
        Comment comment = Instancio.of(Comment.class).create();
        Dream dream = Instancio.of(Dream.class).create();
        dreamRepository.save(dream);
        comment.setDream(dream);
        Comment comment2 = commentRepository.save(comment);
        comment2.setId(1L);
        Comment newComment = commentRepository.findById(comment2.getId()).orElse(null);
        Long commentId = newComment.getId();
        commentRepository.delete(newComment);
        Comment testComment = commentRepository.findById(commentId).orElse(null);
        Assertions.assertNull(testComment);
    }
}