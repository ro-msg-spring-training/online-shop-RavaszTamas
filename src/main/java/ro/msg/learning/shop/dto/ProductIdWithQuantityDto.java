package ro.msg.learning.shop.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class ProductIdWithQuantityDto {
    private Long productId;
    private Integer quantity;
}
