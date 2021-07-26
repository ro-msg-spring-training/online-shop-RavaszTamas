package ro.msg.learning.shop.converter;

import ro.msg.learning.shop.domain.BaseEntity;

public interface Converter<M extends BaseEntity<Long>, D> {

  M convertDtoToModel(D dto);

  D convertModelToDto(M model);
}
