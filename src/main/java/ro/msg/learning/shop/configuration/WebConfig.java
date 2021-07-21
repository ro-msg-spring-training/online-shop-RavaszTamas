package ro.msg.learning.shop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ro.msg.learning.shop.utilities.CsvMessageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    private HttpMessageConverter<Object> createCsvHttpMessageConverter() {
        return new CsvMessageConverter<>();
    }

}
