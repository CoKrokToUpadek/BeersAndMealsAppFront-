package com.cokroktosmok.beersandmealsappfront.config;
import com.cokroktosmok.beersandmealsappfront.data.dto.user.CreatedUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CreatedUserDto createdUserDto(){
        return new CreatedUserDto();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
