package ro.msg.learning.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.converter.Converter;
import ro.msg.learning.shop.domain.*;
import ro.msg.learning.shop.domain.helperdatastructures.StockToTake;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.exceptions.ResourceNotFoundException;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.strategies.OrderDeliveryStrategy;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImplementation implements OrderService {

    StockRepository stockRepository;
    LocationRepository locationRepository;
    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;
    Converter<Order, OrderDto> orderOrderDtoConverter;

    @Override
    public List<StockToTake> searchForItemsByStrategy(OrderDeliveryStrategy strategy, OrderRequestDto orderRequestDto) {

        return strategy.getListOfStocksToBeFound(orderRequestDto);
    }

    @Override
    public void adjustStock(List<StockToTake> resultingItems) {

        resultingItems.forEach(item -> {
            Optional<Stock> byId = stockRepository.findById(item.getStockId());
            if (byId.isPresent()) {
                Stock stock = byId.get();
                stock.setQuantity(stock.getQuantity() - item.getQuantity());
                stockRepository.save(stock);
            } else
                throw new ResourceNotFoundException("resource wasn't found!");
        });
    }

    @Override
    public Order addOrder(OrderRequestDto orderRequestDto, List<StockToTake> resultingItems) {
        Customer customer = Customer.builder().build();
        customer.setId(orderRequestDto.getCustomerId());
        Location location = Location.builder().build();
        location.setId(resultingItems.get(0).getLocation().getId());
        Order order = Order.builder()
                .address(Address.builder()
                        .streetAddress(orderRequestDto.getStreetAddress())
                        .county(orderRequestDto.getCounty())
                        .country(orderRequestDto.getCountry())
                        .city(orderRequestDto.getCity())
                        .build())
                .createdAt(orderRequestDto.getTimestamp().toLocalDateTime())
                .customer(customer)
                .shippedFrom(location)
                .build();
        Order savedOrder = orderRepository.save(order);
        orderRequestDto.getOrderedItems().forEach(
                orderedItem -> {
                    Product product = Product.builder().build();
                    product.setId(orderedItem.getProductId());
                    OrderDetail newOrderDetail = OrderDetail.builder().order(savedOrder).product(product).quantity(orderedItem.getQuantity()).build();
                    orderDetailRepository.save(newOrderDetail);
                }
        );

        return savedOrder;
    }

    @Override
    public Order createNewOrder(OrderDeliveryStrategy strategy, OrderRequestDto orderRequestDto) {
        List<StockToTake> resultingItems = searchForItemsByStrategy(strategy, orderRequestDto);
        Order newOrder = addOrder(orderRequestDto, resultingItems);
        adjustStock(resultingItems);
        return newOrder;
    }


}
