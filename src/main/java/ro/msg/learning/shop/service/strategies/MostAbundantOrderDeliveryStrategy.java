package ro.msg.learning.shop.service.strategies;

import lombok.AllArgsConstructor;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.Stock;
import ro.msg.learning.shop.domain.helperdatastructures.StockToTake;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.dto.ProductIdWithQuantityDto;
import ro.msg.learning.shop.exceptions.OrderException;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MostAbundantOrderDeliveryStrategy implements OrderDeliveryStrategy {

    private final StockRepository stockRepository;

    @Override
    public List<StockToTake> getListOfStocksToBeFound(OrderRequestDto orderRequestDto) throws OrderException {
        List<StockToTake> resultStocks = orderRequestDto
                .getOrderedItems()
                .stream()
                .map(orderedItem -> {

                    Optional<Stock> theSelectedStock = getStockForOrderedItem(orderedItem);
                    if (theSelectedStock.isPresent()) {
                        return createStockToTake(orderedItem, theSelectedStock.get());
                    } else {
                        throw new OrderException("Resource with id " + orderedItem.getProductId()
                                + " not found for any location!");
                    }

                }).collect(Collectors.toList());
        if (orderRequestDto.getOrderedItems().size() != resultStocks.size())
            throw new OrderException("Resource not found for location!");
        return resultStocks;
    }

    private StockToTake createStockToTake(ProductIdWithQuantityDto orderedItem, Stock theSelectedStock) {
        Product productInTheOrder = Product.builder().build();
        productInTheOrder.setId(orderedItem.getProductId());
        return StockToTake.builder()
                .stockId(theSelectedStock.getId())
                .quantity(orderedItem.getQuantity())
                .location(theSelectedStock.getLocation())
                .product(productInTheOrder)
                .build();
    }

    private Optional<Stock> getStockForOrderedItem(ProductIdWithQuantityDto orderedItem) {
        return stockRepository
                .findTopByProductAndQuantityGreaterThanEqualOrderByQuantityDesc(
                        Product.builder().id(orderedItem.getProductId()).build(),
                        orderedItem.getQuantity()
                );
    }
}
