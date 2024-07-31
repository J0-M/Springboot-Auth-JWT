package com.example.authLogin.Controllers;

import com.example.authLogin.Product.Product;
import com.example.authLogin.Product.ProductRepository;
import com.example.authLogin.Product.ProductRequestDTO;
import com.example.authLogin.Product.ProductResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductRequestDTO data){
        Product productData = new Product(data);
        repository.save(productData);

        return ResponseEntity.status(HttpStatus.CREATED).body("Product Created Sucessfully");
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        List<ProductResponseDTO> list = repository.findAll().stream().map(ProductResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
