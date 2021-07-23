package ro.msg.learning.shop.converter;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Address;
import ro.msg.learning.shop.domain.Customer;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Order;
import ro.msg.learning.shop.dto.OrderDto;

@Component
public class OrderConverter implements BaseConverter<Order, OrderDto> {
    @Override
    public Order convertDtoToModel(OrderDto dto) {

        Customer customer = Customer.builder().build();
        customer.setId(dto.getCustomerId());
        Location location = Location.builder().build();
        location.setId(dto.getShippedFromId());
        return Order.builder()
                .address(Address.builder()
                        .streetAddress(dto.getStreetAddress())
                        .county(dto.getCounty())
                        .country(dto.getCountry())
                        .city(dto.getCity())
                        .build())
                .id(dto.getOrderId())
                .createdAt(dto.getCreatedAt())
                .customer(customer)
                .shippedFrom(location)
                .build();
    }

    @Override
    public OrderDto convertModelToDto(Order model) {
        return OrderDto.builder()
                .country(model.getAddress().getCountry())
                .county(model.getAddress().getCounty())
                .streetAddress(model.getAddress().getStreetAddress())
                .city(model.getAddress().getCity())
                .orderId(model.getId())
                .createdAt(model.getCreatedAt())
                .shippedFromId(model.getShippedFrom().getId())
                .customerId(model.getCustomer().getId())
                .build();
    }
}
