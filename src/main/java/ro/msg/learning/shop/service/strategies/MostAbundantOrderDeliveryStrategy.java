package ro.msg.learning.shop.service.strategies;

import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.Stock;
import ro.msg.learning.shop.domain.helperdatastructures.StockToTake;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.exceptions.OrderException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MostAbundantOrderDeliveryStrategy implements OrderDeliveryStrategy {

    @Override
    public List<StockToTake> getListOfStocksToBeFound(List<Location> locations, OrderRequestDto orderRequestDto) throws OrderException {

        List<StockToTake> resultStocks = orderRequestDto
                .getOrderedItems()
                .stream()
                .map(orderedItem -> {
                    Optional<Location> location = locations.stream()
                            .filter(aLocation ->
                                    aLocation.getStockList().stream()
                                            .anyMatch(stockItem -> stockItem.getProduct().getId()
                                                    .equals(orderedItem.getProductId())))
                            .max(Comparator.comparing(aLocation -> {

                                Optional<Stock> stock = aLocation.getStockList().stream()
                                        .filter(item -> item.getProduct().getId().equals(orderedItem.getProductId()))
                                        .findFirst();
                                if (stock.isPresent())
                                    return stock.get().getQuantity();
                                return -1;
                            }));
                    if (location.isPresent()) {
                        Optional<Stock> theSelectedStock = location.get().getStockList().stream()
                                .filter(item -> item.getProduct().getId().equals(orderedItem.getProductId())).findFirst();
                        if (theSelectedStock.isPresent()) {
                            Product productInTheOrder = Product.builder().build();
                            productInTheOrder.setId(orderedItem.getProductId());
                            return StockToTake.builder()
                                    .stockId(theSelectedStock.get().getId())
                                    .quantity(orderedItem.getQuantity())
                                    .location(location.get())
                                    .product(productInTheOrder)
                                    .build();
                        } else {
                            throw new OrderException("Resource with id " + orderedItem.getProductId()
                                    + " not found for any location!");
                        }
                    }
                    throw new OrderException("Resource with id " + orderedItem.getProductId()
                            + " not found for any location!");

                }).collect(Collectors.toList());

        if (orderRequestDto.getOrderedItems().size() != resultStocks.size())
            throw new OrderException("Resource not found for location!");
        return resultStocks;
    }
}
