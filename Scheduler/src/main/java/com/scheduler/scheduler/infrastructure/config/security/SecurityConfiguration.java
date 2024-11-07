package com.scheduler.scheduler.infrastructure.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/sign-api/sing-in",
                                "sign-api/sign-up",
                                "sign-api/exception").permitAll() // 패턴 해당 요청 모두 허용
                        .requestMatchers(HttpMethod.POST, "/user/**").permitAll()   // POST: /user/** 모두 허용
                        .requestMatchers("**exception**").permitAll()   // **exception** 모두 허용
                        .anyRequest().hasRole("ADMIN"));    // 기타요청 -> ADMIN
        httpSecurity
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .accessDeniedHandler(new CustomAccessDinedHandler())    // 권한 확인 예외
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));   // 인증 과정 예외
        httpSecurity
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),  //
                        UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityConfigure(){
        return (web)->web.ignoring()
                .requestMatchers(
                        "/swagger-ui/**"
                );
    }
}
