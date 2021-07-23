package ro.msg.learning.shop.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "CUSTOMER")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"orderList"})
@ToString(callSuper = true, exclude = {"orderList"})
@AttributeOverride(name = "id", column = @Column(name = "CUSTOMER_ID"))
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Customer extends BaseEntity<Long> {

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @OneToMany(mappedBy = "customer", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orderList;

}
