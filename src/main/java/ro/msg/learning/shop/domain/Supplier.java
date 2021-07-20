package ro.msg.learning.shop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SUPPLIER")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "SUPPLIER_ID"))
public class Supplier extends BaseEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "supplier", orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Product> productList;

}
