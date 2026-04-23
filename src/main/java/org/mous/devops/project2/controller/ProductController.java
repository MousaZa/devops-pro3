package org.mous.devops.project2.controller;


import org.apache.coyote.BadRequestException;
import org.mous.devops.project2.exception.ResourceNotFoundException;
import org.mous.devops.project2.models.Product;
import org.mous.devops.project2.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        System.out.printf("Product: %s\n", products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws BadRequestException {
        try {
            // test
            if (product.getName() == null || product.getName().isEmpty()) {
                throw new BadRequestException("Product name cannot be empty");
            }
            return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
        } catch (Exception e) {
            if (e instanceof BadRequestException) {
                throw e;
            }
            throw new BadRequestException("Failed to create product: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        if (productDetails.getName() != null) {
            product.setName(productDetails.getName());
        }

        return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        productRepository.delete(product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}