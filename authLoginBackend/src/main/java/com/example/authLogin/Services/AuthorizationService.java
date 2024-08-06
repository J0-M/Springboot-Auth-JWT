package com.example.authLogin.Services;

//um serviço neste programa serão classes de apoio para realizar alguma tarefa mais complexa
//neste caso, um serviço de autorização

import com.example.authLogin.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // para o spring identificar que a classe é um service

//o UserDeatilsService serve para que o programa chame automaticamente esse service para garantir autorização
//pois herda (implementa) as funções da classe userDetailsService do security
public class AuthorizationService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//consulta de usuários
        return userRepository.findByLogin(username);
    }
}
