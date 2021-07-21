package ro.msg.learning.shop.converter;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Customer;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Order;
import ro.msg.learning.shop.dto.OrderDto;

@Component
public class OrderConverter implements BaseConverter<Order, OrderDto>{
    @Override
    public Order convertDtoToModel(OrderDto dto) {

        Customer customer = Customer.builder().build();
        customer.setId(dto.getCustomerId());
        Location location = Location.builder().build();
        location.setId(dto.getShippedFromId());
        Order order =  Order.builder()
                .address(dto.getAddress())
                .createdAt(dto.getCreatedAt())
                .customer(customer)
                .shippedFrom(location)
                .build();
        order.setId(dto.getOrderId());
        return order;
    }

    @Override
    public OrderDto convertModelToDto(Order model) {
        return OrderDto.builder()
                .address(model.getAddress())
                .orderId(model.getId())
                .createdAt(model.getCreatedAt())
                .shippedFromId(model.getShippedFrom().getId())
                .customerId(model.getCustomer().getId())
                .build();
    }
}
