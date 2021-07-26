package ro.msg.learning.shop.service;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Stock;

import java.util.List;

@Service
public interface StockService {

  List<Stock> getAllStocks();
}
