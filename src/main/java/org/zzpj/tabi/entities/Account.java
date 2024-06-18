package org.zzpj.tabi.entities;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public class Account implements UserDetails {

    @Id
    @Column(
        name = "id",
        columnDefinition = "UUID",
        unique = true,
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(
        unique = true,
        nullable = false
    )
    private String login;

    @Column
    private boolean locked;

    @Column(
        unique = true,
        nullable = false
    )
    private String email;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Account(String name, String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.login = name;
        this.email = email;
        this.role = Roles.CLIENT;
        this.locked = false;
    }

    public void block() {
        this.locked = true;
    }

    public void unblock() {
        this.locked = false;
    }
}
