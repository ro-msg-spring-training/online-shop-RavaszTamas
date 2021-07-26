package ro.msg.learning.shop.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "PRODUCT_CATEGORY")
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(
    callSuper = true,
    exclude = {"productList"})
@ToString(
    callSuper = true,
    exclude = {"productList"})
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_CATEGORY_ID"))
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory extends BaseEntity<Long> {
  @Column(name = "NAME")
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @OneToMany(mappedBy = "productCategory", orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Product> productList;
}
