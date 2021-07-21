package ro.msg.learning.shop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.converter.Converter;
import ro.msg.learning.shop.domain.Order;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.strategies.OrderDeliveryStrategy;

@RestController
@RequestMapping("/orders")
public class OrderController {

    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderDeliveryStrategy strategy;

    @Autowired
    private Converter<Order, OrderDto> orderConverter;

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Order newOrder = service.createNewOrder(strategy, orderRequestDto);

        return ResponseEntity.ok(orderConverter.convertModelToDto(newOrder));
    }

}
