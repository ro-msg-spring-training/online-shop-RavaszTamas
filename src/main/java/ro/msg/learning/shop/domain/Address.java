package ro.msg.learning.shop.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Address implements Serializable {

    private String country;

    private String city;

    private String county;

    private String streetAddress;

}
