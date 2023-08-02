package com.codecool.elproyectegrande1.repository;

import com.codecool.elproyectegrande1.ElProyecteGrande1;
import com.codecool.elproyectegrande1.config.H2TestProfileJPAConfig;
import com.codecool.elproyectegrande1.entity.*;
import com.codecool.elproyectegrande1.mapper.NewDreamerMapper;
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
import java.util.ArrayList;
import java.util.HashSet;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ElProyecteGrande1.class, H2TestProfileJPAConfig.class })
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DreamRepository dreamRepository;

    @Autowired
    private DreamerRepository dreamerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    void givenCommentRepository_whenSaveAndRetrieveEntity_thenOK() {
        User user = getUser();
        User newUser = userRepository.save(user);
        Comment newComment = new Comment("New comment", 0, "Trung", newUser);
        Comment comment = commentRepository.save(newComment);
        comment.setId(1L);
        Comment foundEntity = commentRepository.findById(comment.getId()).orElse(null);
        Assertions.assertNotNull(foundEntity);
        Assertions.assertEquals(comment.getCommentText(), foundEntity.getCommentText());
    }

    @Test
    void shouldLikeCommentWhenRetrievedFromRepository() {
        User user = getUser();
        User newUser = userRepository.save(user);
        Comment newComment = new Comment("New comment", 0, "Trung", newUser);
        Comment comment = commentRepository.save(newComment);
        comment.setId(1L);
        Comment actual = commentRepository.findById(comment.getId()).orElse(null);
        Assertions.assertNotNull(actual);
        actual.setLikes(comment.getLikes() + 1);
        Assertions.assertEquals(actual.getLikes(), 1);
    }

    @Test
    void shouldUpdateCommentWhenRetrievedFromRepository() {
        User user = getUser();
        User newUser = userRepository.save(user);
        Comment newComment = new Comment("New comment", 0, "Trung", newUser);
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
        User user = getUser();
        User newUser = userRepository.save(user);
        Comment newComment = new Comment("New comment", 0, "Trung", newUser);
        Image dreamImage = imageRepository.save(new Image());
        Dream dream = new Dream("title", "description", new ArrayList<>(), dreamImage);
        Dreamer dreamer = new Dreamer("thomas", "thomas@gmail.com", "password", new HashSet<>());
        Dreamer testDreamer = dreamerRepository.save(dreamer);
        Dreamer actual = dreamerRepository.findById(testDreamer.getId()).orElse(null);
        dream.setDreamer(actual);
        dreamRepository.save(dream);
        newComment.setDream(dream);
        Comment comment2 = commentRepository.save(newComment);
        comment2.setId(1L);
        Comment nextComment = commentRepository.findById(comment2.getId()).orElse(null);
        Long commentId = nextComment.getId();
        commentRepository.delete(nextComment);
        Comment testComment = commentRepository.findById(commentId).orElse(null);
        Assertions.assertNull(testComment);
    }

    private User getUser() {
        User user = new User("Trung", "trung@gmail.com", "123456");
        Dreamer dreamer = new Dreamer("dreamer", "dreamer@gmail.com", "password", new HashSet<>());
        Mentor mentor = new Mentor("mentor", "mentor@gmail.com", "password");
        Avatar avatar = new Avatar();
        HashSet dreamers = new HashSet<>();
        HashSet mentors = new HashSet<>();
        dreamer.setId(1L);
        mentor.setId(1L);
        dreamers.add(dreamer);
        mentors.add(mentor);
        Avatar profilePicture = avatarRepository.save(avatar);
        setUserFields(dreamers, mentors, profilePicture, user);
        return user;
    }

    void setUserFields(HashSet<Dreamer> dreamers, HashSet<Mentor> mentors, Avatar avatar, User user) {
        user.setId(1L);
        user.setFollowedDreamers(dreamers);
        user.setFollowedMentors(mentors);
        user.setProfilePicture(avatar);
    }
}