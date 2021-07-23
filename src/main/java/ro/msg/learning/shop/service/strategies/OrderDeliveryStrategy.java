package ro.msg.learning.shop.service.strategies;

import ro.msg.learning.shop.domain.helperdatastructures.StockToTake;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.exceptions.OrderException;

import java.util.List;


public interface OrderDeliveryStrategy {

    List<StockToTake> getListOfStocksToBeFound(OrderRequestDto orderRequestDto) throws OrderException;
}
