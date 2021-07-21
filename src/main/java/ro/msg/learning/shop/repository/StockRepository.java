package ro.msg.learning.shop.repository;

import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Stock;

import java.util.List;


public interface StockRepository extends Repository<Stock, Long> {

    List<Stock> findByLocation(Location location);

}
