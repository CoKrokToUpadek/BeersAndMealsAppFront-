package com.cokroktosmok.beersandmealsappfront.config;
import com.cokroktosmok.beersandmealsappfront.data.dto.user.CreatedUserDto;
import com.cokroktosmok.beersandmealsappfront.errorhandlers.TeaPotCodeInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class BeanConfig {
    @Bean
    public RestTemplate restTemplate() {
        TeaPotCodeInterceptor teaPotCodeInterceptor=new TeaPotCodeInterceptor( HttpStatus.I_AM_A_TEAPOT,"Token expired");
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(teaPotCodeInterceptor));
        return restTemplate;
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
