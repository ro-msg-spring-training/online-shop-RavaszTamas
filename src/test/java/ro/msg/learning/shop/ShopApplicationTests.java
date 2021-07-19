package ro.msg.learning.shop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ShopApplicationTests {

	@Test
	void contextLoads() {
		Assert.isTrue(true, "loaded context");
		assertTrue(true);
	}

}
