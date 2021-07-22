package ro.msg.learning.shop.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class StockDto {

    Long stockId;
    Long productId;
    Long locationId;
    Integer quantity;

}
