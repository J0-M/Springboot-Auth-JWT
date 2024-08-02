package com.example.authLogin.User;

//um enum é uma "classe" que limita e define um atributo de uma classe java
//nesse caso, o role só pode ser ADMIN ou CLIENT

//Funciona como se fosse um tipo customizável, ou um struct (com funções próprias), se preferir
public enum UserRole {
    ADMIN("admin"),
    CLIENT("client");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
