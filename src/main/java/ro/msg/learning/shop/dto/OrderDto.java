package ro.msg.learning.shop.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class OrderDto {

  private Long orderId;
  private Long shippedFromId;
  private Long customerId;
  private LocalDateTime createdAt;
  private String country;
  private String county;
  private String streetAddress;
  private String city;
}
