package ro.msg.learning.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.exceptions.ResourceNotFoundException;
import ro.msg.learning.shop.exceptions.ValidatorException;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.service.validators.Validator;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final Validator<Product> productValidator;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long productId) {
        if (productId == null)
            throw new ResourceNotFoundException("Id must not be null!");
        return productRepository.findById(productId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Product doesn't exist");
        });
    }

    @Override
    public Product addProduct(Product product) throws ValidatorException {
        productValidator.validate(product);
        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(Long productId) {
        Optional<Product> productByProductId = productRepository.findById(productId);
        if (productByProductId.isPresent()) {
            productRepository.delete(productByProductId.get());
            return productByProductId.get();
        }
        throw new ResourceNotFoundException("Product not found");
    }

    @Override
    public Product updateProduct(Long productId, Product productToUpdate) {
        productValidator.validate(productToUpdate);
        Optional<Product> productByProductId = productRepository.findById(productId);
        if (productByProductId.isPresent()) {
            Product product = productByProductId.get();
            product.setDescription(productToUpdate.getDescription());
            product.setImageUrl(productToUpdate.getImageUrl());
            product.setName(productToUpdate.getName());
            product.setPrice(productToUpdate.getPrice());
            product.setWeight(productToUpdate.getWeight());
            product.setProductCategory(productToUpdate.getProductCategory());
            product.setSupplier(productToUpdate.getSupplier());
            return productRepository.save(product);
        }
        throw new ResourceNotFoundException("Product not found");

    }
}
