package ro.msg.learning.shop.controller;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class OrderController {

    private final OrderDeliveryStrategy strategy;

    private final CustomerRepository customerRepository;

    private final Converter<Order, OrderDto> orderConverter;

    private final OrderService orderService;

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Optional<Customer> customerOptional = customerRepository.findById(orderRequestDto.getCustomerId());
        Order newOrder;
        if (customerOptional.isPresent()) {
            newOrder = orderService.createNewOrder(strategy, orderRequestDto);
            emailService.sendOrderConfirmationMessage(customerOptional.get(),newOrder);
        } else
            throw new OrderException("Customer doesn't exists!");
        return ResponseEntity.ok(orderConverter.convertModelToDto(newOrder));
    }

}
