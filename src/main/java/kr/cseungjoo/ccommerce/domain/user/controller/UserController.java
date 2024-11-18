package kr.cseungjoo.ccommerce.domain.user.controller;


import kr.cseungjoo.ccommerce.domain.user.User;
import kr.cseungjoo.ccommerce.domain.user.dto.*;
import kr.cseungjoo.ccommerce.domain.user.service.UserService;
import kr.cseungjoo.ccommerce.global.basic.response.BasicResponse;
import kr.cseungjoo.ccommerce.global.redis.service.RedisService;
import kr.cseungjoo.ccommerce.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RedisService redisService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUser() {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = Long.parseLong(redisService.getData(principal.getToken()));

        User user = userService.getUserById(userId);

        return BasicResponse.ok(new ReturnUserDto(user));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDto dto) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = Long.parseLong(redisService.getData(principal.getToken()));

        User user = userService.updateUser(dto.getUsername(), userId);

        return BasicResponse.ok(new ReturnUserDto(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {
        User user = userService.register(dto);

        return BasicResponse.ok("성공적으로 회원가입이 되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        TokensDto tokensDto = userService.login(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer {"+ tokensDto.getAccessToken() +"}");
        headers.set("Refresh-Token", tokensDto.getRefreshToken());

        return BasicResponse.ok("ok", headers);
    }
}



