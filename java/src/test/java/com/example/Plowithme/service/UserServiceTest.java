package com.example.Plowithme.service;


<<<<<<< HEAD:java/src/test/java/com/example/Plowithme/Service/UserServiceTest.java
=======
import com.example.Plowithme.entity.User;
import com.example.Plowithme.repository.UserRepository;
>>>>>>> 743b8d2a0f68fd19eef6db6cdf0a9cdc7ec7f7bd:java/src/test/java/com/example/Plowithme/service/UserServiceTest.java
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        User User = new User();
        User.setName("kim");

        //when
        Long savedId = userService.join(User);

        //then
        assertEquals(User, userRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        User User1 = new User();
        User1.setName("kim");

        User User2 = new User();
        User2.setName("kim");

        //when
        userService.join(User1);
        userService.join(User2); //예외가 발생

        //then
        fail("예외가 발생해야 한다.");
    }
}