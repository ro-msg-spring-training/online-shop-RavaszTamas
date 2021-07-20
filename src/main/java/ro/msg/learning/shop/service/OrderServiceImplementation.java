package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.*;
import ro.msg.learning.shop.domain.helperdatastructures.StockToTake;
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
public class OrderServiceImplementation implements OrderService {

    @Autowired
    StockRepository stockRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public List<StockToTake> searchForItemsByStrategy(OrderDeliveryStrategy strategy, OrderRequestDto orderRequestDto) {

        List<Location> locations = locationRepository.findAll();

        if (locations.size() != orderRequestDto.getOrderedItems().size())
            throw new ResourceNotFoundException("No single location found!");

        return strategy.getListOfStocksToBeFound(locations, orderRequestDto);
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
                .address(orderRequestDto.getAddress())
                .createdAt(orderRequestDto.getTimestamp().toLocalDateTime())
                .customer(customer)
                .shippedFrom(location)
                .build();
        Order savedOrder = orderRepository.save(order);
        orderRequestDto.getOrderedItems().forEach(
                orderedItem -> {
                    Product product = Product.builder().build();
                    product.setId(orderedItem.getProductId());
                    OrderDetail newOrderDetail = OrderDetail.builder().order(savedOrder).product(product).build();
                    orderDetailRepository.save(newOrderDetail);
                }
        );

        return savedOrder;
    }


}
