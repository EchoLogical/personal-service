package com.avrist.webui.global.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;

import java.security.Key;
import java.util.Date;

@UtilityClass
public class JwtUtil {

    private final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 day

    private static Key getAccessSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private static Key getRefreshSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public static String generateAccessToken(String username, int hour, String secretKey) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (ACCESS_TOKEN_EXPIRATION_TIME) * hour))
                .signWith(getAccessSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String generateRefreshToken(String username, int day, String secretKey) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (REFRESH_TOKEN_EXPIRATION_TIME) * day))
                .signWith(getRefreshSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String extractUsernameFromAccessToken(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(getAccessSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static String extractUsernameFromRefreshToken(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(getRefreshSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean validateAccessToken(String token, String username, String secretKey) {
        return (username.equals(extractUsernameFromAccessToken(token, secretKey)) && !isTokenExpired(token, getAccessSigningKey(secretKey)));
    }

    public static boolean validateRefreshToken(String token, String secretKey) {
        try {
            return !isTokenExpired(token, getRefreshSigningKey(secretKey));
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isTokenExpired(String token, Key key) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
