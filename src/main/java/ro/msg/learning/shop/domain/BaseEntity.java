package ro.msg.learning.shop.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Getter
@Setter
@SuperBuilder
public class BaseEntity<I extends Serializable> implements Serializable {
    @Id
    @GeneratedValue
    protected I id;
}
