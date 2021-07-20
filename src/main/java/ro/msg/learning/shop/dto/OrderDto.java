package ro.msg.learning.shop.dto;

import lombok.*;
import ro.msg.learning.shop.domain.Address;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class OrderDto {

    Long id;
    Long shippedFromId;
    Long customerId;
    LocalDateTime createdAt;
    Address address;

}
