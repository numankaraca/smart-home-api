package com.example.smarthomeapi.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // Bu, token'ları imzalamak için kullanılacak gizli anahtardır.
    // Gerçek bir uygulamada bu anahtarın çok daha güvenli bir yerden (örn: konfigürasyon dosyası) gelmesi gerekir.
    private static final String SECRET_KEY = "c21hcnQtbW9tZS1hcGktc2VjcmV0LWtleS1mb3ItandrLWF1dGhlbnRpY2F0aW9u";
    private final SecretKey key;

    public JwtService() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Kullanıcı bilgileriyle bir JWT oluşturan metod
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Gelecekte token'ın içine roller gibi ekstra bilgiler eklemek için bu 'claims' yapısını kullanabiliriz.
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // Token'ın sahibi (kullanıcı adı)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Token'ın oluşturulma zamanı
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token'ın geçerlilik süresi (10 saat)
                .signWith(key) // Gizli anahtarla imzala
                .compact();
    }
}