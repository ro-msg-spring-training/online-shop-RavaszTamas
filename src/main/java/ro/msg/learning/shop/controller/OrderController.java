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
import ro.msg.learning.shop.domain.Customer;
import ro.msg.learning.shop.domain.Order;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.exceptions.OrderException;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.service.EmailService;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.strategies.OrderDeliveryStrategy;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderDeliveryStrategy strategy;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Converter<Order, OrderDto> orderConverter;

    @Autowired
    private OrderService orderService;
    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Optional<Customer> customerOptional = customerRepository.findById(orderRequestDto.getCustomerId());
        Order newOrder;
        if (customerOptional.isPresent()) {
            newOrder = orderService.createNewOrder(strategy, orderRequestDto);
            emailService.sendSimpleMessage(customerOptional.get().getEmailAddress(), "test subject", "test text");
        } else
            throw new OrderException("Customer doesn't exists!");
        return ResponseEntity.ok(orderConverter.convertModelToDto(newOrder));
    }

}
