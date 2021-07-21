package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;

@Service
public class StockServiceImplementation implements StockService{

    @Autowired
    StockRepository stockRepository;

    @Override
    public List<Stock> exportStockForLocation(Location location) {
        return stockRepository.findByLocation(location);
    }
}
