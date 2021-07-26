package ro.msg.learning.shop.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "REVENUE")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "REVENUE_ID"))
public class Revenue extends BaseEntity<Long> {

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "LOCATION_ID")
  private Location location;

  @Column(name = "DATE_RECORDED")
  private LocalDate date;

  @Column(name = "CALCULATED_SUM")
  private BigDecimal sum;
}
