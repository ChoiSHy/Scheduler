package com.scheduler.scheduler.infrastructure.config.security;

import com.scheduler.scheduler.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/sign-api/**").permitAll() // sign-api 관련 기능

                        .requestMatchers(HttpMethod.POST, "/user/user-info").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/user/**").authenticated()
                        
                        .requestMatchers("**exception**").permitAll()   // exception 포함 모두 허용
                        .anyRequest().permitAll()   // 기타 요청 허용
                )

                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .accessDeniedHandler(new CustomAccessDinedHandler())    // 권한 확인 예외
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))   // 인증 과정 예외

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityConfigure() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/swagger-config",
                        "/v3/api-docs"
                );
    }
}
