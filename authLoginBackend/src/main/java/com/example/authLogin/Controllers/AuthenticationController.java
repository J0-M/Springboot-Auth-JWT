package com.example.authLogin.Controllers;


import com.example.authLogin.Infra.Security.TokenService;
import com.example.authLogin.User.*;
import jakarta.validation.Valid;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Controller de autenticação, é aqui onde será feita a validação e autenticação do usuário
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;// classe já implementada no security para autenticação

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        //não é boa prática salvar as senhas no BD, pois é uma fragilidade de segurança, por isso, é utilizado o sistema de hashes
        //e criptografias de senhas para salvar e consultar senhas no login
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());//devido ao userRepository, o security consegue buscar usuários no BD
        //para gerar o token, é necessário uma variável que une os valores de login, ou seja, um "usernamePassword"

        var auth = this.authenticationManager.authenticate(usernamePassword);
        //ou seja, nessa variável, o token será gerado pelo login e senha e será sendo validado, baseado na hash gerada pela senha

        var token = tokenService.generateToken((User) auth.getPrincipal());//gera o token e atribui ao usuário

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        repository.save(newUser);

        return ResponseEntity.ok().build();
    }//devido as senhas serem criptografadas e aramazenadas em hashes, é possível que dois usuários possuam a mesma senha
    //porém, como a hash gerada também depende do login do usuário, mesmo que 2 ou mais usuários tenham a mesma senha, não será o o esmo hash
    //ou seja, basta validar para que os usuários não tenham o mesmo login


}
