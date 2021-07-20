package ro.msg.learning.shop.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.ProductCategory;
import ro.msg.learning.shop.domain.Supplier;
import ro.msg.learning.shop.dto.ProductCategoryDto;
import ro.msg.learning.shop.dto.ProductDto;

@Component
public class ProductConverter implements BaseConverter<Product, ProductDto> {

    @Autowired
    Converter<ProductCategory, ProductCategoryDto> productCategoryDtoConverter;

    @Override
    public Product convertDtoToModel(ProductDto dto) {
        Supplier supplier = Supplier.builder().build();
        supplier.setId(dto.getSupplierId());
        Product product = Product.builder().productCategory(productCategoryDtoConverter.convertDtoToModel(dto.getProductCategory()))
                .description(dto.getDescription())
                .name(dto.getName())
                .price(dto.getPrice())
                .weight(dto.getWeight())
                .supplier(supplier)
                .imageUrl(dto.getImageUrl()).build();
        product.setId(dto.getProductId());
        return product;
    }

    @Override
    public ProductDto convertModelToDto(Product model) {
        return ProductDto.builder()
                .productId(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .imageUrl(model.getImageUrl())
                .price(model.getPrice())
                .weight(model.getWeight())
                .productCategory(productCategoryDtoConverter.convertModelToDto(model.getProductCategory()))
                .supplierId(model.getSupplier().getId())
                .build();
    }
}
