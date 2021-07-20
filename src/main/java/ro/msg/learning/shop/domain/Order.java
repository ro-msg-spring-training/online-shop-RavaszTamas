package ro.msg.learning.shop.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "Orders")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "ORDER_ID"))
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "SHIPPED_FROM", referencedColumnName = "LOCATION_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Location shippedFrom;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Customer customer;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Embedded
    private Address address;


    @OneToMany(mappedBy = "order", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<OrderDetail> orderDetailList;


}
