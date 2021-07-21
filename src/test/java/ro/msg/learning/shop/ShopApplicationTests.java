package ro.msg.learning.shop;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import ro.msg.learning.shop.repository.SupplierRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
class ShopApplicationTests {

	@Autowired
	ApplicationContext context;

	@Test
	void contextLoads() {
		Assert.isTrue(true, "loaded context");
		assertTrue(true);
	}


}
