package ro.msg.learning.shop.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_DETAIL")
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "ORDER_DETAIL_ID"))
public class OrderDetail extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "ORDER_ID", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", insertable = false, updatable = false)
    private Product product;

    @Column(name = "QUANTITY")
    private Integer quantity;


}
