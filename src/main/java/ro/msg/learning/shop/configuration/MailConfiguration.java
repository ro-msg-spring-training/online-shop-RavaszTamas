package ro.msg.learning.shop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

  @Value("${spring.mail.host}")
  String host;

  @Value("${spring.mail.properties.mail.smtp.port}")
  Integer port;

  @Value("${spring.mail.username}")
  String username;

  @Value("${spring.mail.password}")
  String password;

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);

    mailSender.setUsername(username);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "false");
    props.put("mail.debug", "true");

    return mailSender;
  }

  @Value("${mail.template.subject}")
  String subject;

  @Bean
  public SimpleMailMessage templateMessage() {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setText(
        "Dear #{ #user } we would like to inform you that we have successfully processed your order. #{ #year } #{ #month } #{ #day }");
    message.setSubject(subject);
    return message;
  }
}
