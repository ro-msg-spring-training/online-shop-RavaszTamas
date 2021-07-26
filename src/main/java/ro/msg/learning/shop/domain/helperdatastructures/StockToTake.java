package ro.msg.learning.shop.domain.helperdatastructures;

import lombok.*;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.domain.Product;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;

@Data
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "STOCK_ID"))
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockToTake {

  private Long stockId;

  private Product product;

  private Location location;

  private Integer quantity;
}
