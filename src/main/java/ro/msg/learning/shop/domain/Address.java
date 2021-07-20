package ro.msg.learning.shop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Address implements Serializable {

    private String Country;

    private String City;

    private String County;

    private String StreetAddress;

}
