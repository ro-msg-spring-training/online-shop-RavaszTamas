package ro.msg.learning.shop.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class StockDto {

  private Long stockId;
  private Long productId;
  private Long locationId;
  private Integer quantity;
}
