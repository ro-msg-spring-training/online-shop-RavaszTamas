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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock extends BaseEntity<Long> {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Product product;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Location location;

    @Column(name = "QUANTITY")
    private Integer quantity;

}
