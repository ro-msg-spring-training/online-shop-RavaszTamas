package ro.msg.learning.shop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.converter.Converter;
import ro.msg.learning.shop.converter.OrderConverter;
import ro.msg.learning.shop.domain.Order;
import ro.msg.learning.shop.domain.helperdatastructures.StockToTake;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.strategies.OrderDeliveryStrategy;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderDeliveryStrategy strategy;

    @Autowired
    private Converter<Order,OrderDto> orderConverter;

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        List<StockToTake> resultingItems = service.searchForItemsByStrategy(strategy, orderRequestDto);
        Order newOrder = service.addOrder(orderRequestDto, resultingItems);
        service.adjustStock(resultingItems);
        return ResponseEntity.ok(orderConverter.convertModelToDto(newOrder));
    }

}
