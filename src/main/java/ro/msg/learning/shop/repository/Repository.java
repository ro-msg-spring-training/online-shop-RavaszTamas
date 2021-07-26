package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.domain.BaseEntity;

import java.io.Serializable;

public interface Repository<T extends BaseEntity<I>, I extends Serializable>
    extends JpaRepository<T, I> {}
