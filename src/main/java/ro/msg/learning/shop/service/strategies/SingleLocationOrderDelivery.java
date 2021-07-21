package ro.msg.learning.shop.service.strategies;

import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.Stock;
import ro.msg.learning.shop.domain.helperdatastructures.StockToTake;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.exceptions.OrderException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SingleLocationOrderDelivery implements OrderDeliveryStrategy {
    @Override
    public List<StockToTake> getListOfStocksToBeFound(List<Location> allLocations, OrderRequestDto orderRequestDto) throws OrderException {
        Optional<Location> locationOptional = allLocations.stream().filter(location -> {
            List<Stock> myStocks = location.getStockList();
            for (var order : orderRequestDto.getOrderedItems()) {

                Optional<Stock> stock = myStocks.stream()
                        .filter(item -> item.getProduct().getId().equals(order.getProductId()))
                        .findFirst();
                if (stock.isPresent()) {
                    if (stock.get().getQuantity() < order.getQuantity()) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }).findFirst();

        if (locationOptional.isPresent()) {
            Location foundLocation = locationOptional.get();
            return orderRequestDto.getOrderedItems().stream().map(
                    order -> {
                        Product product = Product.builder().build();
                        product.setId(order.getProductId());
                        Optional<Stock> stock = foundLocation.getStockList().stream().filter(i -> i.getProduct().getId()
                                .equals(order.getProductId())).findFirst();
                        if (stock.isPresent())
                            return StockToTake.builder().stockId(stock.get().getId()).location(foundLocation).product(product).quantity(order.getQuantity()).build();
                        throw new OrderException("No single location found!");
                    }
            ).collect(Collectors.toList());
        }
        throw new OrderException("No single location found!");
    }
}
