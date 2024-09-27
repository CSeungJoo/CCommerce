package kr.cseungjoo.ccommerce.domain.user.service;

import kr.cseungjoo.ccommerce.domain.model.Role;
import kr.cseungjoo.ccommerce.domain.user.User;
import kr.cseungjoo.ccommerce.domain.user.dto.LoginDto;
import kr.cseungjoo.ccommerce.domain.user.dto.RegisterDto;
import kr.cseungjoo.ccommerce.domain.user.dto.TokensDto;
import kr.cseungjoo.ccommerce.domain.user.exception.AlreadyUsingEmailException;
import kr.cseungjoo.ccommerce.domain.user.exception.EmailNotFoundException;
import kr.cseungjoo.ccommerce.domain.user.exception.LoginFailedException;
import kr.cseungjoo.ccommerce.domain.user.repository.UserRepository;
import kr.cseungjoo.ccommerce.global.jwt.JwtService;
import kr.cseungjoo.ccommerce.global.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder pwdEncoder;
    private final RedisService redisService;

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
        }catch (UsernameNotFoundException e){
            return false;
        }
        return true;
    }
}
