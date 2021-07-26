package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.msg.learning.shop.domain.Location;

import java.util.List;

public interface LocationRepository extends Repository<Location, Long> {

  @Query("select distinct l from Location l")
  @EntityGraph(value = "locationWithStocks", type = EntityGraph.EntityGraphType.LOAD)
  List<Location> findAllWithStocks();
}
