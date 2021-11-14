package com.alirizakocas.filestorage.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Table(name = "USER")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @NonNull
    @NotNull
    @Size(
            min = 4,
            max = 45,
            message = "The username '${validatedValue}' must be between {min} and {max} characters long"
    )
    @Column(name = "USERNAME", nullable = false, length = 100)
    private String username;

    @NonNull
    @NotNull
    @Size(
            min = 8,
            max = 30,
            message = "The password '${validatedValue}' must be between {min} and {max} characters long"
    )
    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } //false olursa User account has expired

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } //false olursa user account is locked hatası alıyoruz.

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // false olursa User credentials have expired hatası alıyoruz.

    @Override
    public boolean isEnabled() {
        return true;
    } // false olursa user is disabled hatası dönüyor.
}

