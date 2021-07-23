package ro.msg.learning.shop.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SUPPLIER")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true, exclude = {"productList"})
@Getter
@Setter
@ToString(callSuper = true, exclude = {"productList"})
@AttributeOverride(name = "id", column = @Column(name = "SUPPLIER_ID"))
public class Supplier extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "supplier", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> productList;

}
