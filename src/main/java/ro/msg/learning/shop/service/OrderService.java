package ro.msg.learning.shop.service;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Order;
import ro.msg.learning.shop.domain.helperdatastructures.StockToTake;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.service.strategies.OrderDeliveryStrategy;

import java.util.List;

@Service
public interface OrderService {
    List<StockToTake> searchForItemsByStrategy(OrderDeliveryStrategy strategy, OrderRequestDto orderRequestDto);

    void adjustStock(List<StockToTake> resultingItems);


    Order addOrder(OrderRequestDto orderRequestDto, List<StockToTake> resultingItems);

    Order createNewOrder(OrderDeliveryStrategy strategy, OrderRequestDto orderRequestDto);
}
