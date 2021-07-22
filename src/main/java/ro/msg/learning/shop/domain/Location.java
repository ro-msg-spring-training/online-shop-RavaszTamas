package ro.msg.learning.shop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "LOCATION")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "LOCATION_ID"))
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(
        name = "locationWithStocks",
        attributeNodes = @NamedAttributeNode(value = "stockList")
)
public class Location extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "shippedFrom", orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Order> orderList;

    @OneToMany(mappedBy = "location", orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Revenue> revenueList;

    @OneToMany(mappedBy = "location", orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Stock> stockList;
}
