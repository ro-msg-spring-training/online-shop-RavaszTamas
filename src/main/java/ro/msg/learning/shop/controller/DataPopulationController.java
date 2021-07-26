package ro.msg.learning.shop.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.domain.*;
import ro.msg.learning.shop.repository.*;

import java.math.BigDecimal;

@RestController
@Profile(value = "test")
@RequestMapping("/populate-data")
@AllArgsConstructor
public class DataPopulationController {

  public static final int QUANTITY_100 = 100;
  public static final int QUANTITY_2000 = 2000;
  public static final String BIG_DECIMAL_VALUE = "2.5";
  public static final double WEIGHT = 5.25;
  public static final String CAT_1 = "cat1";
  public static final String CAT_2 = "cat2";
  public static final String DESCRIPTION_1 = "desc1";
  public static final String DESCRIPTION_2 = "desc2";
  public static final String SUPPLIER_1 = "supplier1";
  public static final String SUPPLIER_2 = "supplier2";
  public static final String STREET_1 = "street 1";
  public static final String STREET_2 = "street 2";
  public static final String STREET_3 = "street 3";
  public static final String CITY_3 = "City 3";
  public static final String CITY_2 = "City 2";
  public static final String CITY_1 = "City 1";
  public static final String COUNTY_1 = "County 1";
  public static final String COUNTRY_1 = "Country 1";
  public static final String COUNTY_2 = "County 2";
  public static final String COUNTRY_2 = "Country 2";
  public static final String COUNTY_3 = "County 3";
  public static final String COUNTRY_3 = "Country 3";
  public static final String PRODUCT_1 = "Product 1";
  public static final String DESC_1 = "Desc 1";
  public static final String WWW_APPLE_1_COM = "www.apple1.com";
  public static final String PRODUCT_2 = "Product 2";
  public static final String DESC_2 = "Desc 2";
  public static final String WWW_APPLE_2_COM = "www.apple2.com";
  public static final String PRODUCT_3 = "Product 3";
  public static final String DESC_3 = "Desc 3";
  public static final String WWW_APPLE_3_COM = "www.apple3.com";
  public static final String PRODUCT_4 = "Product 4";
  public static final String DESC_4 = "Desc 4";
  public static final String WWW_APPLE_4_COM = "www.apple4.com";
  public static final String PRODUCT_5 = "Product 5";
  public static final String DESC_5 = "Desc 5";
  public static final String WWW_APPLE_5_COM = "www.apple5.com";
  private final OrderRepository orderRepository;

  private final ProductRepository productRepository;

  private final ProductCategoryRepository productCategoryRepository;

  private final SupplierRepository supplierRepository;

  private final CustomerRepository customerRepository;

  private final LocationRepository locationRepository;

  private final StockRepository stockRepository;

  @PostMapping
  public ResponseEntity<String> populateDatabase() {
    ProductCategory productCategoryOne =
        productCategoryRepository.save(
            ProductCategory.builder().name(CAT_1).description(DESCRIPTION_1).build());
    ProductCategory productCategoryTwo =
        productCategoryRepository.save(
            ProductCategory.builder().name(CAT_2).description(DESCRIPTION_2).build());

    Supplier supplierOne = supplierRepository.save(Supplier.builder().name(SUPPLIER_1).build());
    Supplier supplierTwo = supplierRepository.save(Supplier.builder().name(SUPPLIER_2).build());

    customerRepository.save(
        Customer.builder()
            .firstName("Name")
            .lastName("Name")
            .userName("username")
            .password("password")
            .emailAddress("address@address.com")
            .build());

    Location locationOne =
        locationRepository.save(
            Location.builder()
                .name("First location")
                .address(
                    Address.builder()
                        .streetAddress(STREET_1)
                        .city(CITY_1)
                        .county(COUNTY_1)
                        .country(COUNTRY_1)
                        .build())
                .build());
    Location locationTwo =
        locationRepository.save(
            Location.builder()
                .name("Second location")
                .address(
                    Address.builder()
                        .streetAddress(STREET_2)
                        .city(CITY_2)
                        .county(COUNTY_2)
                        .country(COUNTRY_2)
                        .build())
                .build());
    Location locationThree =
        locationRepository.save(
            Location.builder()
                .name("Third location")
                .address(
                    Address.builder()
                        .streetAddress(STREET_3)
                        .city(CITY_3)
                        .county(COUNTY_3)
                        .country(COUNTRY_3)
                        .build())
                .build());
    Product productOne =
        productRepository.save(
            Product.builder()
                .supplier(supplierOne)
                .productCategory(productCategoryOne)
                .name(PRODUCT_1)
                .description(DESC_1)
                .price(new BigDecimal(BIG_DECIMAL_VALUE))
                .weight(WEIGHT)
                .imageUrl(WWW_APPLE_1_COM)
                .build());
    Product productTwo =
        productRepository.save(
            Product.builder()
                .supplier(supplierOne)
                .productCategory(productCategoryTwo)
                .name(PRODUCT_2)
                .description(DESC_2)
                .price(new BigDecimal(BIG_DECIMAL_VALUE))
                .weight(WEIGHT)
                .imageUrl(WWW_APPLE_2_COM)
                .build());
    Product productThree =
        productRepository.save(
            Product.builder()
                .supplier(supplierTwo)
                .productCategory(productCategoryTwo)
                .name(PRODUCT_3)
                .description(DESC_3)
                .price(new BigDecimal(BIG_DECIMAL_VALUE))
                .weight(WEIGHT)
                .imageUrl(WWW_APPLE_3_COM)
                .build());
    Product productFour =
        productRepository.save(
            Product.builder()
                .supplier(supplierTwo)
                .productCategory(productCategoryOne)
                .name(PRODUCT_4)
                .description(DESC_4)
                .price(new BigDecimal(BIG_DECIMAL_VALUE))
                .weight(WEIGHT)
                .imageUrl(WWW_APPLE_4_COM)
                .build());
    productRepository.save(
        Product.builder()
            .supplier(supplierTwo)
            .productCategory(productCategoryOne)
            .name(PRODUCT_5)
            .description(DESC_5)
            .price(new BigDecimal(BIG_DECIMAL_VALUE))
            .weight(WEIGHT)
            .imageUrl(WWW_APPLE_5_COM)
            .build());

    stockRepository.save(
        Stock.builder().product(productOne).location(locationOne).quantity(QUANTITY_100).build());
    stockRepository.save(
        Stock.builder().product(productTwo).location(locationOne).quantity(QUANTITY_100).build());
    stockRepository.save(
        Stock.builder().product(productTwo).location(locationTwo).quantity(QUANTITY_2000).build());
    stockRepository.save(
        Stock.builder()
            .product(productThree)
            .location(locationThree)
            .quantity(QUANTITY_100)
            .build());
    stockRepository.save(
        Stock.builder()
            .product(productFour)
            .location(locationThree)
            .quantity(QUANTITY_100)
            .build());
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
