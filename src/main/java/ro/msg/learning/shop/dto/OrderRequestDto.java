package ro.msg.learning.shop.dto;

import lombok.*;
import ro.msg.learning.shop.domain.Address;

import java.sql.Timestamp;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class OrderRequestDto {
    Timestamp timestamp;
    Long customerId;
    Address address;
    @ToString.Exclude
    List<ProductIdWithQuantity> orderedItems;
}
