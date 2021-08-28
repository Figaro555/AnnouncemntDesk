package com.announcementdesk.filters.jwt;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Log4j2
public class JwtManager {

    @Value("$(jwt.secret)")
    private String secretKey;

    public String generateToken(String name) {
        log.log(Level.INFO, "Generating JWT");
        Date expirationDate = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(name)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.log(Level.WARN, "Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.log(Level.WARN, "Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.log(Level.WARN, "Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.log(Level.WARN, "Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.log(Level.WARN, "JWT token compact of handler are invalid.");
        }
        return false;
    }

    public String getNameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
