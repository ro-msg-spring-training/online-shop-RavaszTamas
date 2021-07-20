package ro.msg.learning.shop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "PRODUCT_CATEGORY")
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_CATEGORY_ID"))
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory extends BaseEntity<Long> {
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "productCategory", orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Product> productList;

}
