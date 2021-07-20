package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.converter.ProductConverter;
import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductConverter productConverter;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productConverter.convertModelsToDtos(productService.findAll()));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productConverter.convertModelToDto(productService.addProduct(productConverter.convertDtoToModel(productDto))));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productConverter.convertModelToDto(productService.updateProduct(productId, productConverter.convertDtoToModel(productDto))));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productConverter.convertModelToDto(productService.findById(productId)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productConverter.convertModelToDto(productService.deleteProduct(productId)));
    }
}
