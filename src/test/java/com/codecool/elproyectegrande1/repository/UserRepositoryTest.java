package com.codecool.elproyectegrande1.repository;

import com.codecool.elproyectegrande1.ElProyecteGrande1;
import com.codecool.elproyectegrande1.config.H2TestProfileJPAConfig;
import com.codecool.elproyectegrande1.entity.ERole;
import com.codecool.elproyectegrande1.entity.Role;
import com.codecool.elproyectegrande1.entity.User;
import com.codecool.elproyectegrande1.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ElProyecteGrande1.class, H2TestProfileJPAConfig.class })
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Slf4j
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void init() {
        sessionFactory = HibernateUtil.getSessionFactory();
        log.info("testing session");
    }

    @Test
    void givenDreamerRepository_whenSaveAndRetrieveEntity_thenOK() {
        //given:
        User user = createUserWithRoles();

        //when:
        User foundEntity = userRepository.findByUsername(user.getUsername()).orElse(null);

        //then:
        Assertions.assertNotNull(foundEntity);
        Assertions.assertEquals(user.getUsername(), foundEntity.getUsername());
    }

    private User createUserWithRoles() {
        Role admin = new Role(ERole.ROLE_ADMIN);
        HashSet<Role> roles = new HashSet<>();
        roles.add(admin);
        User user = userRepository.save(new User("test", "test@com.pl", "test"));

        user.setId(1L);
        user.setRoles(roles);
        return user;
    }

    @AfterClass
    public static void cleanUp() {
        sessionFactory.close();
    }
}
