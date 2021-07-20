package ro.msg.learning.shop.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "STOCK")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "STOCK_ID"))
public class Stock extends BaseEntity<Long>{

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID", insertable = false, updatable = false)
    private Location location;

    @Column(name = "QUANTITY")
    private Integer quantity;

}
