package com.example.authLogin.Infra.Security;
//classe (serviço) para gerar os tokens do usuário
//utiliza a biblioteca Java JWT, para instalar, basta adicionar a dependencia no pom.xml

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.authLogin.User.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")//o secret é gerado a partir de váriáveis presentes no interior do S.O, definidos na apllication properties
    //porém, caso não seja possível estabelecer uma secret com variáveis do ambiente, é possível definir a própria secret com um valor personalizado
    //JWT_SECRET = variável de ambiente, my-secret-key = valor customizado
    private String secret;

    public String generateToken(User user){//necessário receber o usuário como parametro para fazer checagem de role e etc para gerar o token
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);//algoritmo já pronto para geração de tokens, presente no JWT
            //essa função do algorithm deve receber um "secret" (chave) para criptografar e gerar hashes únicas e indecifráveis
            //sem a presença da secret

            String token = JWT.create()
                    .withIssuer("auth-api")//quem gerou o token, indica de qual aplicação o token foi gerado
                    .withSubject(user.getLogin())//quem está recebendo esse token
                    .withExpiresAt(generateExpirationDate())//tempo de validade do token (quanto tempo até expirar)
                    .sign(algorithm);//assinatura do token, algoritmo e secret utilizado para criptografia
            return token;

        }catch (JWTCreationException exception){
            throw new RuntimeException("Error While Generating Token", exception);
        }
    }

    //função para validar o token atribuído ao usuário, quando ele disparar uma requisição Http
    //valida se o token é o que geramos, se não está expirado, se permite tal requisição, etc.
    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)//tipo de geração do token (algoritmo que gerou)
                    .withIssuer("auth-api")//a aplicação que gerou o token
                    .build()
                    .verify(token)//verifica o token
                    .getSubject();//consulta e retorna o usuário relacionado ao token
        } catch (JWTVerificationException exception){
            return "";//ocorre erro caso o token esteja expirado ou o token não tenha sido gerado pela aplicação
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        //basicamente, pegamos o atual momento, somamos 2 horas (tempo de expiração estabelecido do token) e convertemos para o fuso horário de Brasília
    }
}
