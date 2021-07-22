package ro.msg.learning.shop;


import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ro.msg.learning.shop.domain.*;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.dto.ProductIdWithQuantity;
import ro.msg.learning.shop.exceptions.OrderException;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.strategies.MostAbundantOrderDeliveryStrategy;
import ro.msg.learning.shop.service.strategies.OrderDeliveryStrategy;
import ro.msg.learning.shop.service.strategies.SingleLocationOrderDelivery;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ShopApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(
        locations = "classpath:application-integration-single-location-test.properties")
@ActiveProfiles(profiles = "test")
public class StrategyUnitTests {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderService orderService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StockRepository stockRepository;

    Address testAddress = Address
            .builder()
            .streetAddress("AnAddress 1")
            .city("Cluj")
            .county("Cluj")
            .country("RO")
            .build();

    @Before
    public void setUp() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/populate-data")).andExpect(status().isOk());

    }

    @After
    public void tearDown() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/populate-data")).andExpect(status().isOk());
    }

    @Test
    public void whenRequestingOrderFromSingleLocation_thenLocationIsFound() {

        OrderDeliveryStrategy strategy = new SingleLocationOrderDelivery();

        List<Location> locationList = locationRepository.findAllWithStocks();

        Optional<Customer> customer = customerRepository.findAll().stream().findFirst();

        List<Product> productList = productRepository.findAll();

        if (customer.isPresent()) {
            List<ProductIdWithQuantity> requested = productList.stream()
                    .filter(item -> item.getName().equals("Product 1") || item.getName().equals("Product 2"))
                    .map(item -> ProductIdWithQuantity.builder().productId(item.getId()).quantity(10).build())
                    .collect(Collectors.toList());

            OrderRequestDto orderRequestDto = OrderRequestDto
                    .builder()
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .customerId(customer.get().getId())
                    .address(testAddress)
                    .orderedItems(requested)
                    .build();

            Assertions.assertDoesNotThrow(()->{strategy.getListOfStocksToBeFound(locationList,orderRequestDto);});
        } else {
            throw new RuntimeException("Invalid data initialization!");
        }
    }
    @Test
    public void whenRequestingOrderFromSingleLocation_thenLocationIsNotFound() {

        OrderDeliveryStrategy strategy = new SingleLocationOrderDelivery();

        List<Location> locationList = locationRepository.findAllWithStocks();

        Optional<Customer> customer = customerRepository.findAll().stream().findFirst();

        List<Product> productList = productRepository.findAll();

        if (customer.isPresent()) {
            List<ProductIdWithQuantity> requested = productList.stream()
                    .filter(item -> item.getName().equals("Product 1") || item.getName().equals("Product 4"))
                    .map(item -> ProductIdWithQuantity.builder().productId(item.getId()).quantity(10).build())
                    .collect(Collectors.toList());

            OrderRequestDto orderRequestDto = OrderRequestDto
                    .builder()
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .customerId(customer.get().getId())
                    .address(testAddress)
                    .orderedItems(requested)
                    .build();

            Assertions.assertThrows(OrderException.class,()-> strategy.getListOfStocksToBeFound(locationList,orderRequestDto));
        } else {
            throw new RuntimeException("Invalid data initialization!");
        }
    }
    @Test
    public void whenRequestingOrderFromAbundantLocation_thenLocationIsFound() {

        OrderDeliveryStrategy strategy = new MostAbundantOrderDeliveryStrategy();

        List<Location> locationList = locationRepository.findAllWithStocks();

        Optional<Customer> customer = customerRepository.findAll().stream().findFirst();

        List<Product> productList = productRepository.findAll();

        if (customer.isPresent()) {
            List<ProductIdWithQuantity> requested = productList.stream()
                    .filter(item -> item.getName().equals("Product 1") || item.getName().equals("Product 2"))
                    .map(item -> ProductIdWithQuantity.builder().productId(item.getId()).quantity(10).build())
                    .collect(Collectors.toList());

            OrderRequestDto orderRequestDto = OrderRequestDto
                    .builder()
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .customerId(customer.get().getId())
                    .address(testAddress)
                    .orderedItems(requested)
                    .build();

            Assertions.assertDoesNotThrow(()->{strategy.getListOfStocksToBeFound(locationList,orderRequestDto);});
        } else {
            throw new RuntimeException("Invalid data initialization!");
        }
    }
    @Test
    public void whenRequestingOrderFromAbundantLocation_thenLocationIsNotFound() {

        OrderDeliveryStrategy strategy = new SingleLocationOrderDelivery();

        List<Location> locationList = locationRepository.findAllWithStocks();

        Optional<Customer> customer = customerRepository.findAll().stream().findFirst();

        List<Product> productList = productRepository.findAll();

        if (customer.isPresent()) {
            List<ProductIdWithQuantity> requested = productList.stream()
                    .filter(item -> item.getName().equals("Product 1") || item.getName().equals("Product 5"))
                    .map(item -> ProductIdWithQuantity.builder().productId(item.getId()).quantity(10).build())
                    .collect(Collectors.toList());

            OrderRequestDto orderRequestDto = OrderRequestDto
                    .builder()
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .customerId(customer.get().getId())
                    .address(testAddress)
                    .orderedItems(requested)
                    .build();

            Assertions.assertThrows(OrderException.class,()-> strategy.getListOfStocksToBeFound(locationList,orderRequestDto));
        } else {
            throw new RuntimeException("Invalid data initialization!");
        }
    }

}
