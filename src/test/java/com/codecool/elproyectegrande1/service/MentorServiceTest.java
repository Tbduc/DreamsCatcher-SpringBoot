package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.dto.mentor.MentorDto;
import com.codecool.elproyectegrande1.dto.offer.NewOfferDto;
import com.codecool.elproyectegrande1.dto.offer.OfferDto;
import com.codecool.elproyectegrande1.dto.user.UserDto;
import com.codecool.elproyectegrande1.entity.*;
import com.codecool.elproyectegrande1.mapper.MentorMapper;
import com.codecool.elproyectegrande1.mapper.OfferMapper;
import com.codecool.elproyectegrande1.mapper.UserMapper;
import com.codecool.elproyectegrande1.repository.*;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MentorServiceTest {

    @Mock
    private AvatarRepository avatarRepository;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private MentorRepository mentorRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MentorMapper mentorMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private OfferMapper offerMapper;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private MentorService sut;

    @Test
    void shouldAddOffer() {
        //given:
        User user = Instancio.of(User.class).create();
        NewOfferDto newOfferDto = Instancio.of(NewOfferDto.class).create();
        Offer offer = Instancio.of(Offer.class).create();
        OfferDto offerDto = Instancio.of(OfferDto.class).create();
        List<Offer> offers = new ArrayList<>();
        offers.add(offer);
        Mentor mentor = new Mentor(user.getUsername(), user.getEmail(), user.getPassword());
        mentor.setOffers(offers);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(mentor));
        when(offerMapper.mapOfferDtoToEntity(newOfferDto)).thenReturn(offer);
        when(offerRepository.save(offer)).thenReturn(offer);
        when(offerMapper.mapEntityToOfferDto(offer)).thenReturn(offerDto);

        //when:
        OfferDto actual = sut.addOffer(user.getUsername(), newOfferDto);

        //then:
        assertNotNull(actual);
    }

    @Test
    void createMentorFromUser() {
        //given:
        User user = Instancio.of(User.class).create();
        HashSet<Role> roles = new HashSet<>();
        Role mentorRole = new Role(ERole.ROLE_MENTOR);
        roles.add(mentorRole);
        Mentor mentor = new Mentor(user.getUsername(), user.getEmail(), user.getPassword());
        mentor.setRoles(roles);
        when(mentorMapper.mapUserToMentor(user)).thenReturn(mentor);
        when(userRepository.save(user)).thenReturn(mentor);

        //when:
        sut.createMentorFromUser(user, roles);

        //then:
        assertNotNull(mentor);
    }

    @Test
    void getAllMentors() {
        //given
        Mentor mentor1 = Instancio.of(Mentor.class).create();
        Mentor mentor2 = Instancio.of(Mentor.class).create();

        when(userRepository.findAll()).thenReturn(List.of(mentor1, mentor2));

        UserDto mentorDto1 = Instancio.of(UserDto.class).create();
        UserDto mentorDto2 = Instancio.of(UserDto.class).create();

        when(userMapper.mapEntityToDto(mentor1)).thenReturn(mentorDto1);
        when(userMapper.mapEntityToDto(mentor2)).thenReturn(mentorDto2);

        //when
        List<UserDto> allMentors = sut.getAllMentors();

        //then
        assertEquals(allMentors, List.of(mentorDto1, mentorDto2));
    }


    @Test
    void getAllOffersByMentorNickname() {
        //given
        List<OfferDto> offers = new ArrayList<>();
        User user = Instancio.of(User.class).create();
        Mentor mentor = new Mentor(user.getUsername(), user.getEmail(), user.getPassword());
        mentor.setId(user.getId());

        //when
        when(userRepository.findByUsername(eq(user.getUsername()))).thenReturn(Optional.of(mentor));
        when(sut.getAllOffersByMentorNickname(user.getUsername())).thenReturn(offers);

        //then
        assertNotNull(offers);
    }

    @Test
    void getMentorByNickname() {
        //given:
        User user = Instancio.of(User.class).create();
        Mentor mentor = new Mentor(user.getUsername(), user.getEmail(), user.getPassword());
        MentorDto mentorDto = Instancio.of(MentorDto.class).create();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(mentor));
        when(mentorMapper.mapEntityToDto(mentor)).thenReturn(mentorDto);

        //when:
        MentorDto actual = sut.getMentorByNickname(mentor.getUsername());

        //then:
        assertEquals(actual, mentorDto);
    }

    @Test
    void followMentor() {
        //given:
        User user = Instancio.of(User.class).create();
        User userGoogle = Instancio.of(User.class).create();
        Mentor mentor = new Mentor("Trung", user.getEmail(), user.getPassword());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(mentor.getUsername())).thenReturn(Optional.of(mentor));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(userGoogle));

        //when:
        sut.followMentor(mentor.getUsername(), user.getUsername());

        //then:
        assertEquals(1, mentor.getFollowers());
    }

    @Test
    void unfollowMentor() {
        //given:
        User user = Instancio.of(User.class).create();
        User userGoogle = Instancio.of(User.class).create();
        Mentor mentor = new Mentor("Trung", user.getEmail(), user.getPassword());
        mentor.setFollowers(1);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(mentor.getUsername())).thenReturn(Optional.of(mentor));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(userGoogle));

        //when:
        sut.unfollowMentor(mentor.getUsername(), user.getUsername());

        //then:
        assertEquals(0, mentor.getFollowers());
    }

    @Test
    void getAllUnverifiedMentors() {
        //given:
        Mentor mentor1 = Instancio.of(Mentor.class).create();
        Mentor mentor2 = Instancio.of(Mentor.class).create();
        mentor1.setVerified(false);
        mentor2.setVerified(false);

        when(userRepository.findAll()).thenReturn(List.of(mentor1, mentor2));

        UserDto mentorDto1 = Instancio.of(UserDto.class).create();
        UserDto mentorDto2 = Instancio.of(UserDto.class).create();

        when(userMapper.mapEntityToDto(mentor1)).thenReturn(mentorDto1);
        when(userMapper.mapEntityToDto(mentor2)).thenReturn(mentorDto2);

        //when:
        List<UserDto> allMentors = sut.getAllUnverifiedMentors();

        //then:
        assertEquals(allMentors, List.of(mentorDto1, mentorDto2));
        assertEquals(allMentors.get(0).isVerified(), mentorDto1.isVerified());
        assertEquals(allMentors.get(1).isVerified(), mentorDto2.isVerified());
    }
}