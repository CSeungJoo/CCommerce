package kr.cseungjoo.ccommerce.domain.user.service;

import kr.cseungjoo.ccommerce.domain.model.Role;
import kr.cseungjoo.ccommerce.domain.user.User;
import kr.cseungjoo.ccommerce.domain.user.dto.LoginDto;
import kr.cseungjoo.ccommerce.domain.user.dto.RegisterDto;
import kr.cseungjoo.ccommerce.domain.user.dto.TokensDto;
import kr.cseungjoo.ccommerce.domain.user.exception.*;
import kr.cseungjoo.ccommerce.domain.user.repository.UserRepository;
import kr.cseungjoo.ccommerce.global.jwt.JwtService;
import kr.cseungjoo.ccommerce.global.mail.MailService;
import kr.cseungjoo.ccommerce.global.redis.service.RedisService;
import kr.cseungjoo.ccommerce.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.FailedLoginException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder pwdEncoder;
    private final RedisService redisService;
    private final MailService mailService;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                UserNotFoundException::new
        );
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                EmailNotFoundException::new
        );
    }

    public TokensDto login(LoginDto loginDto) {
        User user = getUserByEmail(loginDto.getEmail());
        if(pwdEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            String accessToken = jwtService.createAccessToken(user.getRole().toString());
            String refreshToken = jwtService.createRefreshToken(user.getRole().toString());
            TokensDto tokensDto = new TokensDto(accessToken, refreshToken);

            redisService.setData(accessToken, user.getId().toString());

            return tokensDto;
        }

        throw new LoginFailedException();
    }

    @Transactional
    public User register(RegisterDto registerDto) {
        if (alreadyUsingEmail(registerDto.getEmail()))
            throw new AlreadyUsingEmailException();

        User user = User.builder()
                .email(registerDto.getEmail())
                .password(pwdEncoder.encode(registerDto.getPassword()))
                .username(registerDto.getUsername())
                .role(Role.GUEST)
                .build();

        return userRepository.save(user);
    }

    public boolean alreadyUsingEmail(String email) {
        try {
            getUserByEmail(email);
        }catch (EmailNotFoundException e){
            return false;
        }
        return true;
    }

    public void sendValidationLink(long userId) {
        User user = getUserById(userId);

        String encodingString = pwdEncoder.encode(user.getId() + user.getCreatedAt().toString());

        redisService.setData(encodingString, user.getId().toString());

        mailService.sendEmail(user.getEmail(), "이메일 인증 링크입니다.", mailService.serverProtocol +"://"+ mailService.serverHost +":"+ mailService.serverPort +"/validation/"+ encodingString);
    }

    @Transactional
    public TokensDto validation(String encodingString) throws BadRequestException {
        String data = redisService.getData(encodingString);

        if (data == null)
            throw new BadRequestException();

        Long userId = Long.valueOf(data);
        User user = getUserById(userId);

        if (!user.getRole().equals(Role.GUEST))
            throw new AlreadyValidationException();

        user.setRole(Role.USER);

        String accessToken = jwtService.createAccessToken(user.getRole().toString());
        String refreshToken = jwtService.createRefreshToken(user.getRole().toString());

        redisService.setData(accessToken, user.getId().toString());

        return new TokensDto(accessToken, refreshToken);
    }

    @Transactional
    public User updateUser(String username, long userId) {
        User user = getUserById(userId);
        user.setUsername(username);

        return userRepository.save(user);
    }

}
