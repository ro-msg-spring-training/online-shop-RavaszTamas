package ro.msg.learning.shop.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class ProductCategoryDto {
  private Long productCategoryId;
  private String name;
  private String description;
}
