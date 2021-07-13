package com.example.ecommerceweb.jwt;

import com.example.ecommerceweb.model.CustomUserDetail;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.common.util.impl.Log;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.ErrorManager;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String JWT_SECRET = "secret";
    private final long JWT_EXPIRATION = 604800000L;

    public String generateToken(CustomUserDetail userDetail){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder().setSubject(Long.toString(userDetail.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public String getUsernameFromJWTToken(String token){
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        }catch (MalformedJwtException ex){
            log.error("Invalid JWT token");
        }catch (ExpiredJwtException ex){
            log.error("Expired JWT token");
        }catch (UnsupportedJwtException ex){
            log.error("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            log.error("JWT claims string is empty");
        }
        return false;
    }
}
