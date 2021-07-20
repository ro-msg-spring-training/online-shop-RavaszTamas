package ro.msg.learning.shop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import ro.msg.learning.shop.repository.SupplierRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ShopApplicationTests {

	@Autowired
	ApplicationContext context;

	@Test
	void contextLoads() {
		Assert.isTrue(true, "loaded context");
		assertTrue(true);
	}

	@Test
	void testFindAllSuppliers(){
		context.getBean(SupplierRepository.class);
	}

}
