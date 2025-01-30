package com.totalMiniBmw.tmb_server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.totalMiniBmw.tmb_server.entities.enums.Authority;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Employee Number required.")
    @Min(1)
    private long employeeNumber;

    @NotBlank(message = "First name required.")
    private String firstName;

    @NotBlank(message = "Last name required.")
    private String lastName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email required.")
    private String email;

    @NotBlank(message = "Password required.")
    @JsonIgnore
    private String password;

    @NotBlank(message = "Kiosk token required.")
    private String kioskToken;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "JSONB", name = "authorities")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Authority> authorities = new ArrayList<>(List.of(Authority.NO_ACCESS));

    private Boolean promptToSetPw = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ToolEntity> checkedOut;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> combinedAuthorities = new ArrayList<>();

        // Add the role to the list
        // Add all authorities to the list
        if (authorities != null) {
            authorities.forEach(auth -> combinedAuthorities.add(new SimpleGrantedAuthority(auth.name())));
        }
        return combinedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

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
