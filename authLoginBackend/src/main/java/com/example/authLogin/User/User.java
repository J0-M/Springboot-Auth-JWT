package com.example.authLogin.User;


import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table(name="users")//em postgres NÃO salve uma tabela apenas como "user", pois irá haver conflito no SGBD
@Entity(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")

//UserDetails é uma classe do Spring Security que contém métodos básicos para a validação dos usuários, usando implements, estamos atribuindo esse método à nossa classe Users
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String login;

    private String password;

    private UserRole role;

    public User(String login, String password, UserRole role){
        this.login = login;
        this.password = password;
        this.role = role;
    }//construtor para registro de novo usuário

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_CLIENT"));
        //numa hierarquia de roles, uma role superior possuirá as mesmas permissões de uma role inferior
        //ou seja, neste caso, se um user for ADMIN, é necessário informar que ele tem as permissões de ADMIN e de CLIENT
        //SimpleGrantedAuthority = função do Security que processa as permissões baseadas nas roles do user

        return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
        //é necessário sempre retornar um "List.of" de SimpleGrantedAuthority pois, pelos motivos acima, um usuário pode ter mais de uma role
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
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
