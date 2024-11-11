package com.scheduler.scheduler.infrastructure.config.security;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.domain.User.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserService userService; // Spring Security 에서 제공하는 서비스 레이어
    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);


    @Value("${springboot.jwt.secret}")
    private String secretKey = "secretKey-length-longer-than-32";
    private SecretKey key;
    private final Long expiredTime = 1000L * 60 * 60;

    // SecretKey 에 대해 인코딩 수행
    @PostConstruct
    protected void init() {
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        if(secretKey == null || secretKey.length()<32)
            throw new IllegalArgumentException("JWT Secret Key must be at least 32 characters long");

        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    // JWT 토큰 생성
    public String createToken(String userUid, Role role) {
        LOGGER.info("[createToken] 토큰 생성 시작");
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userUid);
        claims.put("role", role);

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expiredTime);

        String token = Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(now.toInstant()))
                .expiration(Date.from(tokenValidity.toInstant()))
                .signWith(SignatureAlgorithm.HS256, key) // 암호화 알고리즘, secret 값 세팅
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;
    }

    // JWT 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token) {
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        String username = this.getUsername(token);
        LOGGER.info("[getAuthentication] 회원정보: {}", username);
        UserDetails userDetails = userService.loadUserByUsername(username);
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // JWT 토큰에서 회원 구별 정보 추출
    public String getUsername(String token) {
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출. token: {}",token);
        Claims claims = Jwts.parser()
                .verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        String info = claims.get("userId").toString();
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
        return info;
    }

    /**
     * HTTP Request Header 에 설정된 토큰 값을 가져옴
     *
     * @param request Http Request Header
     * @return String type Token 값
     */
    public String resolveToken(HttpServletRequest request) {
        LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }

    // JWT 토큰의 유효성 + 만료일 체크
    public boolean validateToken(String token) {
        LOGGER.info("[validateToken] 토큰 유효 체크 시작");
        if(token == null) return false;

        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(key).build()
                    .parseSignedClaims(token);
            boolean ret = !claims.getBody().getExpiration().before(new Date());
            LOGGER.info("[validateToken] 토큰 유효 체크 완료");
            return ret;
        }catch (io.jsonwebtoken.security.SignatureException e){
            LOGGER.info("[validateToken] SignatureException 발생");
            return false;
        }catch (ExpiredJwtException e){
            LOGGER.info("[validateToken] ExpiredJwtException 발생");
            return false;
        }
        catch (JwtException e) {
            LOGGER.info("[validateToken] JwtException 발생");
            return false;
        }catch (IllegalArgumentException e){
            LOGGER.info("[validateToken] IllegalArgumentException 발생");
            return false;
        }catch (Exception e){
            return false;
        }
    }

}
