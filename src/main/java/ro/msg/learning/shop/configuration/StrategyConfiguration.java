package ro.msg.learning.shop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.service.strategies.MostAbundantOrderDeliveryStrategy;
import ro.msg.learning.shop.service.strategies.OrderDeliveryStrategy;
import ro.msg.learning.shop.service.strategies.SingleLocationOrderDelivery;

@Configuration
public class StrategyConfiguration {

    @Value("${strategy.type}")
    private String strategy;

    @Bean
    OrderDeliveryStrategy orderDeliveryStrategy() {
        if (strategy.equals("abundant"))
            return new MostAbundantOrderDeliveryStrategy();
        else if (strategy.equals("singleLocation"))
            return new SingleLocationOrderDelivery();
        else
            return new SingleLocationOrderDelivery();
    }
}
