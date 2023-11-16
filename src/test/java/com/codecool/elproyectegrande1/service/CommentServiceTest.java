package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.dto.comment.CommentDto;
import com.codecool.elproyectegrande1.dto.comment.NewCommentDto;
import com.codecool.elproyectegrande1.entity.*;
import com.codecool.elproyectegrande1.mapper.CommentMapper;
import com.codecool.elproyectegrande1.repository.AvatarRepository;
import com.codecool.elproyectegrande1.repository.CommentRepository;
import com.codecool.elproyectegrande1.repository.DreamRepository;
import com.codecool.elproyectegrande1.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommentServiceTest {

    @Mock
    private AvatarRepository avatarRepository;

    @Mock
    private DreamRepository dreamRepository;

    @Mock
    private CommentRepository commentRepository;

    @Spy
    private CommentMapper commentMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService sut;

    @Test
    void shouldAddComment() {
        //given:
        NewCommentDto newCommentDto = new NewCommentDto("New comment");
        User user = getUser();
        Dream dream = new Dream("New dream", "Description", new ArrayList<>(), new Image());
        Comment comment = new Comment("text", 0, user.getUsername(), user);
        when(dreamRepository.findById(eq(dream.getId()))).thenReturn(Optional.of(dream));
        when(userRepository.findByUsername(eq(user.getUsername()))).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        //when:
        CommentDto actual = sut.addComment(newCommentDto, user.getUsername(), dream.getId());

        //then:
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(comment.getComment(), actual.getComment());
        verify(commentRepository).save(any(Comment.class));
        verify(dreamRepository).save(any(Dream.class));
    }

    @Test
    void shouldLikeComment() {
        //given:
        User user = getUser();
        Dream dream = new Dream("New dream", "Description", new ArrayList<>(), new Image());
        Comment comment = new Comment("text", 0, user.getUsername(), user);
        when(dreamRepository.findById(eq(dream.getId()))).thenReturn(Optional.of(dream));
        when(userRepository.findByUsername(eq(user.getUsername()))).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentRepository.findById(eq(comment.getId()))).thenReturn(Optional.of(comment));

        //when:
        sut.likeComment(comment.getId(), user.getId());

        //then:
        Assertions.assertEquals(comment.getLikes(), 1);
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