package ro.msg.learning.shop.service;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Customer;
import ro.msg.learning.shop.domain.Order;

@Service
public interface EmailService {
    void sendOrderConfirmationMessage(Customer customer, Order order);
}
