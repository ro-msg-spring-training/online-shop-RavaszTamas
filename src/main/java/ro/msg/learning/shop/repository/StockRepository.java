package ro.msg.learning.shop.repository;

import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.Stock;

import java.util.Optional;

public interface StockRepository extends Repository<Stock, Long> {

  Optional<Stock> findTopByProductAndQuantityGreaterThanEqualOrderByQuantityDesc(
      Product product, Integer quantity);
}
