package ro.msg.learning.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Customer;
import ro.msg.learning.shop.domain.Order;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
public class EmailServiceImplementation implements EmailService {

    public static final String SENDER_MAIL = "noreply@shopapp.com";
    JavaMailSender mailSender;

    SimpleMailMessage template;

    @Override
    public void sendOrderConfirmationMessage(Customer customer, Order order) {

        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();

        context.setVariable("user", customer.getFirstName() + " " + customer.getLastName());
        context.setVariable("year", Calendar.getInstance().get(Calendar.YEAR));
        context.setVariable("month", Calendar.getInstance().get(Calendar.MONTH));
        context.setVariable("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));


        String processedText =
                parser.parseExpression(Objects.requireNonNull(template.getText()), new TemplateParserContext()).getValue(context,String.class);


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER_MAIL);
        message.setTo(customer.getEmailAddress());
        message.setSubject(Objects.requireNonNull(template.getSubject()));
        message.setText(Objects.requireNonNull(processedText));
        mailSender.send(message);

    }
}
