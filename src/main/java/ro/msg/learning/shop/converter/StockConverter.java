package ro.msg.learning.shop.converter;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.domain.Stock;
import ro.msg.learning.shop.dto.StockDto;

@Component
public class StockConverter implements BaseConverter<Stock, StockDto> {
    @Override
    public Stock convertDtoToModel(StockDto dto) {
        Location location = Location.builder().id(dto.getLocationId()).build();
        Product product = Product.builder().id(dto.getProductId()).build();
        return Stock.builder().id(dto.getStockId()).quantity(dto.getQuantity()).location(location).product(product).build();
    }

    @Override
    public StockDto convertModelToDto(Stock model) {
        return StockDto.builder().stockId(model.getId())
                .productId(model.getProduct().getId())
                .locationId(model.getLocation().getId())
                .quantity(model.getQuantity())
                .build();
    }
}
