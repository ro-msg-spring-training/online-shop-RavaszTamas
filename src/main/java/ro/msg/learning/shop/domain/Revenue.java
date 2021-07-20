package ro.msg.learning.shop.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "REVENUE")
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "REVENUE_ID"))
public class Revenue extends BaseEntity<Long>{

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID", insertable = false, updatable = false)
    private Location location;

    @Column(name = "DATE_RECORDED")
    private LocalDate date;

    @Column(name = "CALCULATED_SUM")
    private BigDecimal sum;
}
