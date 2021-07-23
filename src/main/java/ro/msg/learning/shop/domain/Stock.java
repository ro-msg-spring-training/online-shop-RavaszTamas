package ro.msg.learning.shop.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@Table(name = "STOCK")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"location", "product"})
@ToString(callSuper = true, exclude = {"location", "product"})
@AttributeOverride(name = "id", column = @Column(name = "STOCK_ID"))
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Stock extends BaseEntity<Long> {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @Column(name = "QUANTITY")
    private Integer quantity;

}
