package com.example.authLogin.Product;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name="products")
@Entity(name="products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private float price;

    public Product(ProductRequestDTO data){
        this.name = data.name();
        this.price = data.price();
    }
}
