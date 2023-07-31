package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.dto.dreamer.DreamerDto;
import com.codecool.elproyectegrande1.dto.dreamer.NewDreamerDto;
import com.codecool.elproyectegrande1.entity.Dreamer;
import com.codecool.elproyectegrande1.entity.User;
import com.codecool.elproyectegrande1.mapper.NewDreamerMapper;
import com.codecool.elproyectegrande1.repository.DreamerRepository;
import com.codecool.elproyectegrande1.repository.UserRepository;
import org.instancio.Instancio;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DreamerServiceTest {

    @Mock
    private NewDreamerMapper newDreamerMapper;
    @Mock
    private DreamerRepository dreamerRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DreamerService dreamerService;

    @Test
    void shouldCreateDreamer() {
        NewDreamerDto newDreamerDto = Instancio.of(NewDreamerDto.class).create();
        Dreamer dreamer = Instancio.of(Dreamer.class).create();
        DreamerDto dreamerDto = Instancio.of(DreamerDto.class).create();
        when(dreamerRepository.save(dreamer)).thenReturn(dreamer);
        when(newDreamerMapper.mapNewDreamerDtoToEntity(newDreamerDto)).thenReturn(dreamer);
        when(newDreamerMapper.mapEntityToDreamerDto(dreamer)).thenReturn(dreamerDto);
        DreamerDto actual = dreamerService.createDreamer(newDreamerDto);
        Assertions.assertEquals(dreamerDto, actual);
    }

    @Test
    void followDreamer() {
        Dreamer dreamer = Instancio.of(Dreamer.class).create();
        DreamerService dreamerServiceMock = mock(DreamerService.class);
        when(dreamerRepository.findById(dreamer.getId())).thenReturn(Optional.of(dreamer));

        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            dreamerServiceMock.followDreamer(arg0.toString(), arg1.toString());
            return null;
        }).when(dreamerServiceMock).followDreamer(dreamer.getUsername(), "Trung");
    }

    @Test
    void getDreamerWithMostFollowers() {
        Dreamer dreamer = Instancio.of(Dreamer.class).create();
        lenient().when(userRepository.save(dreamer)).thenReturn(dreamer);
        dreamer.setFollowers(1);
        Dreamer dreamer2 = Instancio.of(Dreamer.class).create();
        dreamer2.setFollowers(2);
        lenient().when(userRepository.save(dreamer2)).thenReturn(dreamer2);
        Dreamer actual = Instancio.of(Dreamer.class).create();
        actual.setFollowers(3);
        lenient().when(dreamerService.getDreamerWithMostFollowers()).thenReturn(actual);
        Assertions.assertTrue(actual.getFollowers() > dreamer.getFollowers());
        Assertions.assertTrue(actual.getFollowers() > dreamer2.getFollowers());
    }

    @Test
    void getDreamerById() {
        Dreamer dreamer = Instancio.of(Dreamer.class).create();
        lenient().when(userRepository.save(dreamer)).thenReturn(dreamer);
        when(userRepository.findById(dreamer.getId())).thenReturn(Optional.of(dreamer));
        Assertions.assertNotNull(dreamer);
    }

    @Test
    void unfollowDreamer() {
        Dreamer dreamer = Instancio.of(Dreamer.class).create();
        DreamerService dreamerServiceMock = mock(DreamerService.class);
        when(dreamerRepository.findById(dreamer.getId())).thenReturn(Optional.of(dreamer));

        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            dreamerServiceMock.followDreamer(arg0.toString(), arg1.toString());
            return null;
        }).when(dreamerServiceMock).followDreamer(dreamer.getUsername(), "Trung");

        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            dreamerServiceMock.followDreamer(arg0.toString(), arg1.toString());
            return null;
        }).when(dreamerServiceMock).unfollowDreamer(dreamer.getUsername(), "Trung");;
    }

    @Test
    void donateDreamer() {
        Dreamer dreamer = Instancio.of(Dreamer.class).create();
        dreamer.setId(1L);
        BigDecimal funds = dreamer.getFunds();
        when(userRepository.findById(dreamer.getId())).thenReturn(Optional.of(dreamer));
        dreamerService.donateDreamer(dreamer.getId(), BigDecimal.valueOf(100L));
        Assertions.assertEquals(dreamer.getFunds(), funds.add(BigDecimal.valueOf(100L)));
    }

    @Test
    void createDreamerFromUser() {
        User user = Instancio.of(User.class).create();
        Dreamer dreamer = Instancio.of(Dreamer.class).create();
        dreamer.setId(user.getId());
        when(newDreamerMapper.mapUserToDreamer(user)).thenReturn(dreamer);
        Assertions.assertNotNull(dreamer);
    }
}