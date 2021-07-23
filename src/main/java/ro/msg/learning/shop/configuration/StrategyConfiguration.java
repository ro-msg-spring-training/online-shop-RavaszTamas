package ro.msg.learning.shop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.strategies.MostAbundantOrderDeliveryStrategy;
import ro.msg.learning.shop.service.strategies.OrderDeliveryStrategy;
import ro.msg.learning.shop.service.strategies.SingleLocationOrderDelivery;

@Configuration
public class StrategyConfiguration {

    @Value("${strategy.type}")
    private String strategy;


    @Bean
    OrderDeliveryStrategy orderDeliveryStrategy(StockRepository stockRepository, LocationRepository locationRepository) {
        if (strategy.equals("abundant"))
            return new MostAbundantOrderDeliveryStrategy(stockRepository);
        else if (strategy.equals("singleLocation"))
            return new SingleLocationOrderDelivery(locationRepository);
        else
            return new SingleLocationOrderDelivery(locationRepository);
    }

}
