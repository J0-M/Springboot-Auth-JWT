package com.example.authLogin.Infra.Security;

//classe responsável por desabilitar as configurações padrões do Security, para implementar as nossas prórpias
//configuração padrão do Security = ao iniciar o projeto, gera um token, aí, num broser, devemos digitar
//a rota da função na API, para aí logarmos com o token para termos o retorno da função
//muito trabalhoso, nada flexível e não aplicável em projetos comerciais

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration//indica que é uma classe de configuração
@EnableWebSecurity//habilita a configuração do webSecurity para configuração
public class SecurityConfigurations {
    //um filtro de segurança são as funções que filtram as permissões do usuário, e determina se ele pode ou não fazer algo
    //uma SecurityFilterChain, é um conjunto destes filtros

    @Autowired
    SecurityFilter securityFilter;//filtro onde há a validação de token e permissão de usuários

    @Bean//Necessário para o Spring instanciar a classe, indicando se tratar do security
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())//desabilita essa configuração
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //autenticação stateful = armazena os estados e informações da sessão do usuário
                //autenticação steteless = autenticação via token (MAIS COMUM)

                .authorizeHttpRequests(authorize -> authorize//quais requisições HHTP serão autorizadas
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()//qualquer usuário, mesmo estando autenticado
                        //ou não, pode acessar as funções de login ou registro (pois, senão ,seria redundante)

                        .requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")//bloqueando o metódo POST no endpoint product para apenas usuarios ADMIN
                        .anyRequest().authenticated()//qualquer outra requisição, basta o usuario estar autenticado (qualquer outra role, que não seja post, pode ser feita por qualquer um)
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)//adiciona um filtro antes de autorizar as requisições, para validar os tokens
                .build();//constrói a configuração do filterChain

                //Sem mais nenhuma configuração adicional, apenas datando como stateless, qualquer user pode acessar as funções
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){//função para criptografar as senhas
        return new BCryptPasswordEncoder();//classe do Security já implementada para criptografar senhas
    }
}
