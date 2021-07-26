package ro.msg.learning.shop.converter;

import ro.msg.learning.shop.domain.BaseEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface BaseConverter<M extends BaseEntity<Long>, D> extends Converter<M, D> {

  default List<D> convertModelsToDtos(Collection<M> models) {
    return models.stream().map(this::convertModelToDto).collect(Collectors.toList());
  }

  default List<M> convertDtosToModels(Collection<D> models) {
    return models.stream().map(this::convertDtoToModel).collect(Collectors.toList());
  }
}
