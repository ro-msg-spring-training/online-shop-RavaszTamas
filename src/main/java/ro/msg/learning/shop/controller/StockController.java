package ro.msg.learning.shop.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.converter.BaseConverter;
import ro.msg.learning.shop.domain.Stock;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.service.StockService;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@AllArgsConstructor
public class StockController {

  private final StockService stockService;

  private final BaseConverter<Stock, StockDto> stockDtoConverter;

  @GetMapping
  public List<StockDto> getAllStocks() {
    return stockDtoConverter.convertModelsToDtos(stockService.getAllStocks());
  }
}
