package com.example.smarthomeapi.config.filter;

import com.example.smarthomeapi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // Bizim UserService'imiz bu arayüzü uyguluyor

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. İstekte "Authorization" başlığı var mı ve "Bearer " ile başlıyor mu diye kontrol et.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Eğer yoksa, isteği zincirdeki bir sonraki filtreye geçir ve işlemi bitir.
            return;
        }

        // 2. "Bearer " kısmını atarak sadece token'ı al.
        jwt = authHeader.substring(7);

        // 3. Token'ın içinden kullanıcı adını çıkar.
        username = jwtService.extractUsername(jwt);

        // 4. Kullanıcı adı varsa VE bu istek için daha önce kimlik doğrulaması yapılmamışsa...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Veritabanından kullanıcı bilgilerini yükle.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 5. Token'ın geçerli olup olmadığını kontrol et.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // 6. Eğer token geçerliyse, Spring Security için bir kimlik doğrulama nesnesi oluştur.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. Oluşturulan bu kimlik doğrulama nesnesini Spring Security'nin güvenlik bağlamına yerleştir.
                // Bu, "Bu kullanıcı bu istek için doğrulanmıştır" demektir.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 8. İsteği, zincirdeki bir sonraki filtreye geçir.
        filterChain.doFilter(request, response);
    }
}