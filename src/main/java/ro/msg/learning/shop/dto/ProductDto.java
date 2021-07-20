package ro.msg.learning.shop.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class ProductDto {

    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private ProductCategoryDto productCategory;
    private Long supplierId;
    private String imageUrl;

}
