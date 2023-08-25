package com.example.Plowithme.security;

import com.example.Plowithme.exception.custom.TokenException;
import com.example.Plowithme.exception.error.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration.milliseconds}")
    private long jwtExpirationDate;

    private final CustomUserDetailsService customUserDetailsService;

    //Secret Key를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }
    // JWT 토큰 생성
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", authentication.getAuthorities());

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setClaims(claims)
                //  .signWith(jwtSecret, SignatureAlgorithm.HS256)
                //.claim("rol", String.join(",", authentication.getAuthorities())
                .setIssuedAt(currentDate)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                //.signWith(key())
                .compact();
        return token;
    }


    // 토큰에서 username 추출(email)
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }


    // jwt 토큰 검증
    public boolean validateToken(String token){
        if(token!=null) {
            try {
                log.info("=============토큰검증 시작");
                Jwts.parserBuilder()
                        .setSigningKey(jwtSecret)
                        .build()
                        .parse(token);
                log.info("=============토큰검증 성공");
                return true;

            } catch (MalformedJwtException ex) {
                log.error("잘못된 토큰");
              //  throw new TokenException("잘못된 토큰");
            } catch (SignatureException ex) {
                log.error("유효하지 않은 JWT signature");
              //  throw new TokenException("잘못된 토큰");
            } catch (ExpiredJwtException ex) {
                log.error("만료된 토큰");
              //  throw new TokenException("토큰 기한 만료");
            } catch (UnsupportedJwtException ex) {
                log.error("지원하지 않는 토큰");
               // throw new TokenException("지원하지 않는 토큰");
            } catch (IllegalArgumentException ex) {
                log.error("유효하지 않은 토큰");
               // throw new TokenException("유효하지 않은 토큰");
            }
            return false;
        }
        else{
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }


    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }


}
