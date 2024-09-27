package kr.cseungjoo.ccommerce.domain.user.service;

import kr.cseungjoo.ccommerce.domain.user.User;
import kr.cseungjoo.ccommerce.domain.user.dto.LoginDto;
import kr.cseungjoo.ccommerce.domain.user.dto.TokensDto;
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
                () -> new UsernameNotFoundException("존재 하지 않은 이메일 입니다.")
        );
    }

    public TokensDto login(LoginDto loginDto) throws FailedLoginException {
        User user = getUserByEmail(loginDto.getEmail());
        if(pwdEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            String accessToken = jwtService.createAccessToken(user.getRole());
            String refreshToken = jwtService.createRefreshToken(user.getRole());
            TokensDto tokensDto = new TokensDto(accessToken, refreshToken);

            redisService.setData(accessToken, user.getId().toString());

            return tokensDto;
        }

        throw new FailedLoginException("로그인에 실패하였습니다.");
    }
}
