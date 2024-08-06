package com.example.authLogin.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByLogin(String login);
    //o repositório JPA implementa funções novas pela semântica, nesse caso, ele identifica o findBy
    //sabendo que deve localizar e o Login, identificando que o parâmetro será o atributo login
    //essa função retornará UserDeatils por ser uma classe mais fácil de lidar no spring Security
}
