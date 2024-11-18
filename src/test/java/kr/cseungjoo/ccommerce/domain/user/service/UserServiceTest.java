package kr.cseungjoo.ccommerce.domain.user.service;

import kr.cseungjoo.ccommerce.domain.model.Role;
import kr.cseungjoo.ccommerce.domain.user.User;
import kr.cseungjoo.ccommerce.domain.user.dto.LoginDto;
import kr.cseungjoo.ccommerce.domain.user.dto.RegisterDto;
import kr.cseungjoo.ccommerce.domain.user.dto.TokensDto;
import kr.cseungjoo.ccommerce.domain.user.exception.AlreadyValidationException;
import kr.cseungjoo.ccommerce.domain.user.repository.UserRepository;
import kr.cseungjoo.ccommerce.global.redis.service.RedisService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder pwdEncoder;
    @Autowired
    private RedisService redisService;

    @BeforeEach
    public void init() {
        User user1 = new User(1L, "test1@test.test", pwdEncoder.encode("wow"), "cseungjoo", Role.USER, LocalDateTime.now(), LocalDateTime.now());
        User user2 = new User(2L, "test2@test.test", pwdEncoder.encode("wow"), "test", Role.GUEST, LocalDateTime.now(), LocalDateTime.now());

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void getUserById() {
        User user = userService.getUserById(1L);

        Assertions.assertEquals(user.getUsername(), "cseungjoo");
    }

    @Test
    void getUserByEmail() {
        User user = userService.getUserByEmail("test1@test.test");

        Assertions.assertEquals(user.getUsername(), "cseungjoo");
    }

    @Test
    void login() {
        TokensDto wow = userService.login(new LoginDto("test1@test.test", "wow"));
        System.out.println(wow.getAccessToken());
        System.out.println(wow.getRefreshToken());

        assertNotNull(wow);
    }

    @Test
    void register() {
        User register = userService.register(new RegisterDto("test3@test.test", "wow", "java"));

        List<User> all = userRepository.findAll();

        org.assertj.core.api.Assertions.assertThat(all).hasSize(3);
    }

    @Test
    void sendValidationLink() {
        userService.sendValidationLink(1L);
    }

    @Test
    void alreadyValidation() throws BadRequestException {
        User user = userService.getUserById(1L);

        String encode = pwdEncoder.encode(user.getId() + user.getCreatedAt().toString());
        redisService.setData(encode, "1");

        assertThrows(AlreadyValidationException.class, () -> userService.validation(encode));
    }

    @Test
    void validation() throws BadRequestException {
        User user = userService.getUserById(2L);

        String encode = pwdEncoder.encode(user.getId() + user.getCreatedAt().toString());
        redisService.setData(encode, "2");
        TokensDto validation = userService.validation(encode);

        System.out.println(validation.getAccessToken());
        System.out.println(validation.getRefreshToken());

        assertNotNull(validation);
    }

    @Test
    void updateUser() {
        userService.updateUser("newName", 1L);

        User user = userService.getUserById(1L);

        Assertions.assertEquals("newName", user.getUsername());
    }
}