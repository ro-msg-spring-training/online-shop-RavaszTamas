package ro.msg.learning.shop.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"orderedItems"})
@ToString(exclude = {"orderedItems"})
@Builder
public class OrderRequestDto {
    private Timestamp timestamp;
    private Long customerId;
    private String country;
    private String county;
    private String streetAddress;
    private String city;
    private List<ProductIdWithQuantityDto> orderedItems;
}
