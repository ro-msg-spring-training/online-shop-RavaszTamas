package ro.msg.learning.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;
import ro.msg.learning.shop.domain.*;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.dto.ProductIdWithQuantityDto;
import ro.msg.learning.shop.exceptions.OrderException;
import ro.msg.learning.shop.repository.*;
import ro.msg.learning.shop.service.OrderService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ShopApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(
        locations = "classpath:application-integration-single-location-test.properties")
@ActiveProfiles(profiles = "test")
public class OrderControllerSingleLocationIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private StockRepository stockRepository;

    Address testAddress;

    @Before
    public void setUp() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/populate-data")).andExpect(status().isOk());

        testAddress = Address
                .builder()
                .streetAddress("AnAddress 1")
                .city("Cluj")
                .county("Cluj")
                .country("RO")
                .build();

    }

    @After
    public void tearDown() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/populate-data")).andExpect(status().isOk());
        testAddress = null;
    }

    @Test
    public void givenEnoughStock_forSingleLocation_whenSubmittingOrder_thenStatus200_orderIsCreated() throws Exception {

        Optional<Customer> customer = customerRepository.findAll().stream().findFirst();
        List<Product> productList = productRepository.findAll();
        if (customer.isPresent()) {
            List<ProductIdWithQuantityDto> requested = productList.stream()
                    .filter(item -> item.getName().equals("Product 1") || item.getName().equals("Product 2"))
//                    .filter(item -> item.getName().equals("Product 1"))
                    .map(item -> ProductIdWithQuantityDto.builder().productId(item.getId()).quantity(10).build())
                    .collect(Collectors.toList());

            mvc.perform(MockMvcRequestBuilders.post("/orders")
                    .content(asJsonString(OrderRequestDto
                            .builder()
                            .streetAddress(testAddress.getStreetAddress())
                            .city(testAddress.getCity())
                            .country(testAddress.getCountry())
                            .county(testAddress.getCounty())
                            .customerId(customer.get().getId())
                            .timestamp(new Timestamp(System.currentTimeMillis()))
                            .orderedItems(requested)
                            .build()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").isNotEmpty());

            assertEquals(1, orderRepository.findAll().size());

        } else {
            throw new RuntimeException("Invalid data initialization!");
        }
    }

    @Test(expected = NestedServletException.class)
    public void givenMissingStock_forSingleLocation_whenSubmittingOrder_thenStatus500_orderIsNotCreated() throws Exception {
        Optional<Customer> customer = customerRepository.findAll().stream().findFirst();
        List<Product> productList = productRepository.findAll();
        if (customer.isPresent()) {
            List<ProductIdWithQuantityDto> requested = productList.stream()
                    .filter(item -> item.getName().equals("Product 1") || item.getName().equals("Product 5"))
                    .map(item -> ProductIdWithQuantityDto.builder().productId(item.getId()).quantity(10).build())
                    .collect(Collectors.toList());

            mvc.perform(MockMvcRequestBuilders.post("/orders")
                    .content(asJsonString(OrderRequestDto
                            .builder()
                            .streetAddress(testAddress.getStreetAddress())
                            .city(testAddress.getCity())
                            .country(testAddress.getCountry())
                            .county(testAddress.getCounty())
                            .customerId(customer.get().getId())
                            .timestamp(new Timestamp(System.currentTimeMillis()))
                            .orderedItems(requested)
                            .build()))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof OrderException))
                    .andExpect(status().isInternalServerError());

            assertEquals(0, orderRepository.findAll().size());

        } else {
            throw new RuntimeException("Invalid data initialization!");
        }

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
