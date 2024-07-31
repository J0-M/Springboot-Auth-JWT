package com.example.authLogin.Product;

import java.util.UUID;

public record ProductResponseDTO(UUID id, String name, float price) {
    public ProductResponseDTO(Product product){
        this(product.getId(), product.getName(), product.getPrice());
    }
}
