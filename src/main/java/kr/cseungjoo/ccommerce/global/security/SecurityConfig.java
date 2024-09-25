package kr.cseungjoo.ccommerce.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.cseungjoo.ccommerce.global.security.filter.JsonAuthenticationFilter;
import kr.cseungjoo.ccommerce.global.security.handler.LoginFailureHandler;
import kr.cseungjoo.ccommerce.global.security.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder encoderPwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(encoderPwd());

        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider provider = daoAuthenticationProvider();
        provider.setPasswordEncoder(encoderPwd());
        return new ProviderManager(provider);
    }

    @Bean
    public JsonAuthenticationFilter jsonUsernamePasswordLoginFilter() throws Exception {
        JsonAuthenticationFilter jsonUsernamePasswordLoginFilter = new JsonAuthenticationFilter(objectMapper, authenticationManager());
        jsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        jsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        jsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(new LoginFailureHandler());
        return jsonUsernamePasswordLoginFilter;
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/users/register", "/users/login", "/users/logout").permitAll()
                        .anyRequest().authenticated()
                ).formLogin(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
