package ro.msg.learning.shop.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_ID"))
public class Product extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;


    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "WEIGHT")
    private Double weight;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_CATEGORY_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProductCategory productCategory;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIER_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Supplier supplier;

    @OneToMany(mappedBy = "product", orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<OrderDetail> orderDetailList;


    @OneToMany(mappedBy = "product", orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Stock> stockList;

}
