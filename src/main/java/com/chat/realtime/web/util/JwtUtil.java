package com.chat.realtime.web.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JwtUtil {

    private String SECRET_KEY = "TEST_KEY";

    private long USER_AUTH_EXPIRED_MILLIS = 36000000 * 12;

    private long CONNECT_EXPIRED_MILLIS = 120000; //2분

    private Key secretKey;

    /**
     * User 인증
     *
     * @return
     */
    public String createUserAuthToken(String userId) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + USER_AUTH_EXPIRED_MILLIS))
                .claim("id", userId)
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }

    /**
     * socket connect 인증용 토큰
     * 만료시간 : 30초
     *
     * @return
     */
    public String createConnectToken() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + CONNECT_EXPIRED_MILLIS))
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .claim("uuid", UUID.randomUUID().toString()) // TODO: 2020-09-21  길이 짧은 토큰 발급이 좋을듯
                .compact();
    }

    public boolean isValidToken(String token) {
        log.info("isValidToken :  token " + token);
        try {
            Claims claims = getClaims(token);
            log.info("isValidToken : expireTime :" + claims.getExpiration());
            log.info("isValidToken : Id :" + claims.get("id"));
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
        return LocalDateTime.ofInstant(getClaims(token).getExpiration().toInstant(),
                ZoneId.systemDefault());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
    }

}
