package com.example.authLogin.Infra.Security;

import com.example.authLogin.User.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//OncePerRequestFilter = a cada requisição, esse filtro será acionado uma vez sempre
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    //filtro obrigatório para esse componente, pois é o filtro acionado quando chamamos essa classe nas configurações de segurança
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if(token != null){//onde a validação de role será feita para liberar, ou não, a requisição
            var login = tokenService.validateToken(token);//chama o processa de validação do token da requisição e retorna o usuário relacionado ao token
            UserDetails user = userRepository.findByLogin(login);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());//filtrando as permissões do usuário
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);//chama o próximo filtro, ou seja, esse filtro já foi feito
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");//pega o header da requisição

        if(authHeader == null) return null;//não há nenhum token nessa requisição, ou seja, é uma requisição qeu qualquer um pode acessar

        return authHeader.replace("Bearer ", "");//método padrão por convenção para validar token de requisição
        //pois assim, pegamos apenas o valor bruto do token
    }
}
