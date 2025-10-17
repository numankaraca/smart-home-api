package com.example.smarthomeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // YENİ IMPORT
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails {

    // ... (id, username, password, role alanları aynı kalıyor) ...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;

    // --- YENİ EKLENEN BÖLÜM ---
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Bu çok önemli! Sonsuz döngüyü engeller.
    private List<Room> rooms;
    // ---------------------------

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // --- UserDetails ARAYÜZÜNDEN GELEN ZORUNLU METODLAR ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Kullanıcının rolünü Spring Security'nin anlayacağı formata çeviriyoruz.
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    // Bu metodlar hesap durumuyla ilgilidir. Şimdilik hepsinin 'true' dönmesi yeterli.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}