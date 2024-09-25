package kr.cseungjoo.ccommerce.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/users/register", "/users/login", "/users/logout").permitAll()
                        .anyRequest().authenticated()
                ).formLogin(form -> form
                        .usernameParameter("email")
                );
        return http.build();
    }
}
