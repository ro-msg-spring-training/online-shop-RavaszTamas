package ro.msg.learning.shop.converter;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.ProductCategory;
import ro.msg.learning.shop.dto.ProductCategoryDto;

@Component
public class ProductCategoryConverter
    implements BaseConverter<ProductCategory, ProductCategoryDto> {
  @Override
  public ProductCategory convertDtoToModel(ProductCategoryDto dto) {
    return ProductCategory.builder()
        .description(dto.getDescription())
        .name(dto.getName())
        .id(dto.getProductCategoryId())
        .build();
  }

  @Override
  public ProductCategoryDto convertModelToDto(ProductCategory model) {
    return ProductCategoryDto.builder()
        .productCategoryId(model.getId())
        .description(model.getDescription())
        .name(model.getName())
        .build();
  }
}
