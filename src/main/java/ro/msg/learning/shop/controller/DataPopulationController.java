package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.domain.*;
import ro.msg.learning.shop.repository.*;
import ro.msg.learning.shop.service.OrderService;

import java.math.BigDecimal;

@RestController
@Profile(value = "test")
@RequestMapping("/populate-data")
public class DataPopulationController {

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

    @PostMapping
    public ResponseEntity<String> populateDatabase() {
        ProductCategory productCategoryOne = productCategoryRepository.save(ProductCategory.builder().name("cat1").description("desc1").build());
        ProductCategory productCategoryTwo = productCategoryRepository.save(ProductCategory.builder().name("cat2").description("desc2").build());

        Supplier supplierOne = supplierRepository.save(Supplier.builder().name("supplier1").build());
        Supplier supplierTwo = supplierRepository.save(Supplier.builder().name("supplier2").build());

        customerRepository.save(Customer.builder()
                .firstName("Name").lastName("Name")
                .userName("username").password("password").emailAddress("address@address.com")
                .build());

        Location locationOne = locationRepository.save(Location.builder()
                .name("First location")
                .address(Address.builder().streetAddress("street 1").city("City 1").county("County 1").country("Country 1").build())
                .build());
        Location locationTwo = locationRepository.save(Location.builder()
                .name("Second location")
                .address(Address.builder().streetAddress("street 2").city("City 2").county("County 2").country("Country 2").build())
                .build());
        Location locationThree = locationRepository.save(Location.builder()
                .name("Third location")
                .address(Address.builder().streetAddress("street 3").city("City 3").county("County 3").country("Country 3").build())
                .build());
        Product productOne = productRepository.save(Product.builder()
                .supplier(supplierOne).productCategory(productCategoryOne).name("Product 1").description("Desc 1")
                .price(new BigDecimal("2.5")).weight(5.25).imageUrl("www.apple1.com")
                .build());
        Product productTwo = productRepository.save(Product.builder()
                .supplier(supplierOne).productCategory(productCategoryTwo).name("Product 2").description("Desc 2")
                .price(new BigDecimal("2.5")).weight(5.25).imageUrl("www.apple2.com")
                .build());
        Product productThree = productRepository.save(Product.builder()
                .supplier(supplierTwo).productCategory(productCategoryTwo).name("Product 3").description("Desc 3")
                .price(new BigDecimal("2.5")).weight(5.25).imageUrl("www.apple3.com")
                .build());
        Product productFour = productRepository.save(Product.builder()
                .supplier(supplierTwo).productCategory(productCategoryOne).name("Product 4").description("Desc 4")
                .price(new BigDecimal("2.5")).weight(5.25).imageUrl("www.apple4.com")
                .build());
        productRepository.save(Product.builder()
                .supplier(supplierTwo).productCategory(productCategoryOne).name("Product 5").description("Desc 5")
                .price(new BigDecimal("2.5")).weight(5.25).imageUrl("www.apple5.com")
                .build());

        stockRepository.save(Stock.builder().product(productOne).location(locationOne).quantity(100).build());
        stockRepository.save(Stock.builder().product(productTwo).location(locationOne).quantity(100).build());
        stockRepository.save(Stock.builder().product(productTwo).location(locationTwo).quantity(2000).build());
        stockRepository.save(Stock.builder().product(productThree).location(locationThree).quantity(100).build());
        stockRepository.save(Stock.builder().product(productFour).location(locationThree).quantity(100).build());
        return new ResponseEntity<>("Successful creation", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> clearDatabase() {
        stockRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
        customerRepository.deleteAll();
        supplierRepository.deleteAll();
        productCategoryRepository.deleteAll();
        locationRepository.deleteAll();
        return new ResponseEntity<>("Deletion successful", HttpStatus.OK);
    }

}
