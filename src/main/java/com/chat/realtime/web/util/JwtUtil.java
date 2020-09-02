package com.chat.realtime.web.util;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private String SECRET_KEY = "TEST_KEY";

    long EXPIRED_MILLIS = System.currentTimeMillis() + 36000000 * 12;

    private Key secretKey;

    public String createToken(String userId) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        log.info("들어옴");
        return Jwts.builder()
                .setExpiration(new Date(EXPIRED_MILLIS))
                .claim("id", userId)
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaims(token);
            log.info("expireTime :" + claims.getExpiration());
            log.info("Id :" + claims.get("id"));
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }

    public LocalDateTime getExpiredTime(String token) {
        return  LocalDateTime.ofInstant(getClaims(token).getExpiration().toInstant(),
                ZoneId.systemDefault());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
    }

}
