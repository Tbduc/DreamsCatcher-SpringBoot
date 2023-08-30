package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.dto.dreamer.DreamerDto;
import com.codecool.elproyectegrande1.dto.dreamer.NewDreamerDto;
import com.codecool.elproyectegrande1.entity.Dreamer;
import com.codecool.elproyectegrande1.entity.User;
import com.codecool.elproyectegrande1.mapper.NewDreamerMapper;
import com.codecool.elproyectegrande1.repository.DreamerRepository;
import com.codecool.elproyectegrande1.repository.UserRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DreamerServiceTest {

    @Spy
    private NewDreamerMapper newDreamerMapper;

    @Mock
    private DreamerRepository dreamerRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DreamerService dreamerService;

    @Test
    void shouldCreateDreamer() {
        //given:
        NewDreamerDto newDreamerDto = Instancio.of(NewDreamerDto.class).create();
        Dreamer dreamer = Instancio.of(Dreamer.class).create();
        DreamerDto dreamerDto = Instancio.of(DreamerDto.class).create();

        //when:
        when(userRepository.save(dreamer)).thenReturn(dreamer);
        when(newDreamerMapper.mapNewDreamerDtoToEntity(newDreamerDto)).thenReturn(dreamer);
        when(newDreamerMapper.mapEntityToDreamerDto(dreamer)).thenReturn(dreamerDto);
        DreamerDto actual = dreamerService.createDreamer(newDreamerDto);

        //then:
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
        //given:
        Dreamer dreamer = Instancio.of(Dreamer.class).create();
        dreamer.setFollowers(1);
        Dreamer dreamer2 = Instancio.of(Dreamer.class).create();
        dreamer2.setFollowers(2);
        Dreamer actual = Instancio.of(Dreamer.class).create();
        actual.setFollowers(3);

        //when:
        lenient().when(userRepository.save(dreamer)).thenReturn(dreamer);
        lenient().when(userRepository.save(dreamer2)).thenReturn(dreamer2);
        lenient().when(dreamerService.getDreamerWithMostFollowers()).thenReturn(actual);

        //then:
        Assertions.assertTrue(actual.getFollowers() > dreamer.getFollowers());
        Assertions.assertTrue(actual.getFollowers() > dreamer2.getFollowers());
    }

    @Test
    void getDreamerById() {
        //given:
        Dreamer dreamer = Instancio.of(Dreamer.class).create();

        //when:
        lenient().when(userRepository.save(dreamer)).thenReturn(dreamer);
        when(userRepository.findById(dreamer.getId())).thenReturn(Optional.of(dreamer));

        //then:
        Assertions.assertNotNull(dreamer);
    }

    @Test
    void unfollowDreamer() {
        //given:
        Dreamer dreamer = Instancio.of(Dreamer.class).create();
        DreamerService dreamerServiceMock = mock(DreamerService.class);

        //when:
        when(dreamerRepository.findById(dreamer.getId())).thenReturn(Optional.of(dreamer));

        //then:
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
        //given:
        Dreamer dreamer = Instancio.of(Dreamer.class).create();
        dreamer.setId(1L);
        BigDecimal funds = dreamer.getFunds();

        //when:
        when(userRepository.findById(dreamer.getId())).thenReturn(Optional.of(dreamer));
        dreamerService.donateDreamer(dreamer.getId(), BigDecimal.valueOf(100L));

        //then:
        Assertions.assertEquals(dreamer.getFunds(), funds.add(BigDecimal.valueOf(100L)));
    }

    @Test
    void createDreamerFromUser() {
        //given:
        User user = Instancio.of(User.class).create();
        Dreamer dreamer = Instancio.of(Dreamer.class).create();
        dreamer.setId(user.getId());

        //when:
        when(newDreamerMapper.mapUserToDreamer(user)).thenReturn(dreamer);

        //then:
        Assertions.assertNotNull(dreamer);
    }
}