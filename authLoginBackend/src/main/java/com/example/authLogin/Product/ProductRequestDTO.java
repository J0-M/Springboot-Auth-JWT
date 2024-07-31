package com.example.authLogin.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ProductRequestDTO(@NotBlank @Size(min=1) String name, float price) {
}
