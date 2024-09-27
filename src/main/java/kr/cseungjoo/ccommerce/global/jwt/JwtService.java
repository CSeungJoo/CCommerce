package kr.cseungjoo.ccommerce.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kr.cseungjoo.ccommerce.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtService implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static final String TOKEN_TYPE_KEY = "type";
    private static final String ACCESS_TOKEN_TYPE = "access";
    private static final String REFRESH_TOKEN_TYPE = "refresh";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration_time}")
    private long tokenValidityInMilliseconds;
    @Value("${jwt.refresh_expiration_time}")
    private long refreshTokenValidityInMilliseconds;
    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

//    public String createAccessToken(Authentication authentication) {
//        return createToken(authentication.getAuthorities().toString(), ACCESS_TOKEN_TYPE);
//    }

    public String createAccessToken(String role) {
        return createToken(role, ACCESS_TOKEN_TYPE);
    }

    //    public String createRefreshToken(Authentication authentication) {
//        return createToken(authentication.getAuthorities().toString(), REFRESH_TOKEN_TYPE);
//    }
    public String createRefreshToken(String role) {
        return createToken(role, REFRESH_TOKEN_TYPE);
    }

    public String createToken(String role, String tokenType) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        if (tokenType.equals(REFRESH_TOKEN_TYPE))
            validity.setTime(validity.getTime() + this.refreshTokenValidityInMilliseconds);
        return Jwts.builder()
                .claim("role", role)
                .claim(TOKEN_TYPE_KEY, tokenType)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String role = claims.get("role", String.class);
        PrincipalDetails principalDetails = new PrincipalDetails(token, Collections.singletonList(role));

        return new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public boolean isRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return REFRESH_TOKEN_TYPE.equals(claims.get(TOKEN_TYPE_KEY));
    }

    public boolean isAccessToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return ACCESS_TOKEN_TYPE.equals(claims.get(TOKEN_TYPE_KEY));
    }

    public String refreshAccessToken(String refreshToken) {
        if (validateToken(refreshToken) && isRefreshToken(refreshToken)) {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            return createAccessToken(claims.get("role", String.class));
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}