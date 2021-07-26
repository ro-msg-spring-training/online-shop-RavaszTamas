package ro.msg.learning.shop.service;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Product;

import java.util.List;

@Service
public interface ProductService {
  List<Product> findAll();

  Product findById(Long productId);

  Product addProduct(Product product);

  Product deleteProduct(Long productId);

  Product updateProduct(Long productId, Product product);
}
