package by.park.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class BeanConfiguration {
    @Bean
    public MappingJackson2HttpMessageConverter getMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
}
