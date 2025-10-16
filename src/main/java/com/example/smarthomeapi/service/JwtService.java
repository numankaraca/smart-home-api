package com.example.smarthomeapi.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "c21hcnQtbW9tZS1hcGktc2VjcmV0LWtleS1mb3ItandrLWF1dGhlbnRpY2F0aW9u";
    private final SecretKey key;

    public JwtService() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // --- ESKİ METODLAR (DEĞİŞİKLİK YOK) ---
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 saat geçerlilik
                .signWith(key)
                .compact();
    }

    // --- YENİ EKLENEN METODLAR ---

    // Bir token'dan kullanıcı adını çıkaran metod
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Bir token'ın geçerli olup olmadığını kontrol eden ana metod
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // Token'daki kullanıcı adı, veritabanından gelen kullanıcıyla aynı mı?
        // Ve token'ın süresi dolmuş mu?
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Token'ın süresinin dolup dolmadığını kontrol eder
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Token'dan son geçerlilik tarihini çıkarır
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Token'ın içindeki tüm bilgileri ("claims") çıkaran genel metod
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Belirli bir bilgiyi ("claim") token'dan çıkarmak için kullanılan yardımcı metod
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}