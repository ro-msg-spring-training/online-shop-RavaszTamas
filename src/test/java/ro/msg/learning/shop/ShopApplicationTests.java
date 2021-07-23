package ro.msg.learning.shop;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Test
    void testSpEL() {
        ExpressionParser parser = new SpelExpressionParser();

        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("food", "apple");
        String randomPhrase =
                parser.parseExpression("alma #{ #food }",
                        new TemplateParserContext()).getValue(context,String.class);
        System.out.println(randomPhrase);
        assertNotNull(randomPhrase);
    }



}
