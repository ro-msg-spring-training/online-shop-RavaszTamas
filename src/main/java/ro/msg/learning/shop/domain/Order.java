package ro.msg.learning.shop.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "Orders")
@Getter
@Setter
@EqualsAndHashCode(
    callSuper = true,
    exclude = {"customer", "shippedFrom", "orderDetailList"})
@ToString(
    callSuper = true,
    exclude = {"customer", "shippedFrom", "orderDetailList"})
@AttributeOverride(name = "id", column = @Column(name = "ORDER_ID"))
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Order extends BaseEntity<Long> {

  @ManyToOne
  @JoinColumn(name = "SHIPPED_FROM", referencedColumnName = "LOCATION_ID")
  private Location shippedFrom;

  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID")
  private Customer customer;

  @Column(name = "CREATED_AT")
  private LocalDateTime createdAt;

  @Embedded private Address address;

  @OneToMany(mappedBy = "order", orphanRemoval = true)
  private List<OrderDetail> orderDetailList;
}
