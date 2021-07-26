package ro.msg.learning.shop.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "LOCATION")
@Getter
@Setter
@EqualsAndHashCode(
    callSuper = true,
    exclude = {"orderList", "revenueList", "stockList"})
@ToString(
    callSuper = true,
    exclude = {"orderList", "revenueList", "stockList"})
@AttributeOverride(name = "id", column = @Column(name = "LOCATION_ID"))
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@NamedEntityGraph(
    name = "locationWithStocks",
    attributeNodes = @NamedAttributeNode(value = "stockList"))
public class Location extends BaseEntity<Long> {

  @Column(name = "NAME")
  private String name;

  @Embedded private Address address;

  @OneToMany(mappedBy = "shippedFrom", orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Order> orderList;

  @OneToMany(mappedBy = "location", orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Revenue> revenueList;

  @OneToMany(mappedBy = "location", orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Stock> stockList;
}
