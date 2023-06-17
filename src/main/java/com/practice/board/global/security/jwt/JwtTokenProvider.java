package com.practice.board.global.security.jwt;

import com.practice.board.domain.persistence.user.UserRepository;
import com.practice.board.domain.presentation.dto.response.TokenResponse;
import com.practice.board.global.exception.ExpiredTokenException;
import com.practice.board.global.exception.InvalidTokenException;
import com.practice.board.global.security.auth.CustomUserDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final String secretKey = "entryassignment!@#$!@#$!@#$entryassignment!@#$!@#$!@#$entryassignment!@#$!@#$!@#$";

    private final String prefix = "Bearer ";

    private final Long accessExpiredExp = 60 * 30 * 1000L;

    private final Long refreshExpiredExp = 60 * 60 * 120 * 1000L;

    private final CustomUserDetailsService userDetailsService;

    private final UserRepository userRepository;

    public TokenResponse createToken(String accountId) {

        String accessToken = createAccessToken(accountId);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private String createAccessToken(String accountId) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(accountId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessExpiredExp))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private String createRefreshToken(){

        Date now = new Date();

        String rfToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpiredExp))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return rfToken;
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUserPk(String token){
        try{
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (ExpiredJwtException e){
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e){
            throw InvalidTokenException.EXCEPTION;
        }

    }

    public String resolveToken(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(prefix)
                && bearerToken.length() > prefix.length()+1){
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateTokenExp(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e){
            return false;
        }
    }
}
