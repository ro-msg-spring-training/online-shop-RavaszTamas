package ro.msg.learning.shop.service.strategies;

import lombok.AllArgsConstructor;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.Stock;
import ro.msg.learning.shop.domain.helperdatastructures.StockToTake;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.dto.ProductIdWithQuantityDto;
import ro.msg.learning.shop.exceptions.OrderException;
import ro.msg.learning.shop.repository.LocationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SingleLocationOrderDelivery implements OrderDeliveryStrategy {

    LocationRepository locationRepository;

    @Override
    public List<StockToTake> getListOfStocksToBeFound(OrderRequestDto orderRequestDto) throws OrderException {
        List<Location> locations = locationRepository.findAllWithStocks();

        Optional<Location> locationOptional = getLocationForRequest(orderRequestDto, locations);

        if (locationOptional.isPresent()) {
            Location foundLocation = locationOptional.get();
            return orderRequestDto.getOrderedItems().stream().map(
                    order -> getStockToTake(foundLocation, order)
            ).collect(Collectors.toList());
        }
        throw new OrderException("No single location found!");
    }

    private StockToTake getStockToTake(Location foundLocation, ProductIdWithQuantityDto order) {
        Product product = Product.builder().build();
        product.setId(order.getProductId());
        Optional<Stock> stock = foundLocation.getStockList().stream().filter(i -> i.getProduct().getId()
                .equals(order.getProductId())).findFirst();
        if (stock.isPresent())
            return StockToTake.builder().stockId(stock.get().getId()).location(foundLocation).product(product).quantity(order.getQuantity()).build();
        throw new OrderException("No single location found!");
    }

    private Optional<Location> getLocationForRequest(OrderRequestDto orderRequestDto, List<Location> locations) {
        return locations.stream().filter(location -> {
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
    }
}
