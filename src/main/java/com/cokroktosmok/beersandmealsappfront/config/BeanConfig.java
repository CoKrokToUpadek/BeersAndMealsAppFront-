package com.cokroktosmok.beersandmealsappfront.config;
import com.cokroktosmok.beersandmealsappfront.data.dto.user.CreatedUserDto;
import com.cokroktosmok.beersandmealsappfront.errorhandlers.TeaPotCodeInterceptor;
import com.cokroktosmok.beersandmealsappfront.static_recources.GraphicAssets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Configuration
public class BeanConfig {
    @Bean
    public RestTemplate restTemplate() {
        TeaPotCodeInterceptor teaPotCodeInterceptor=new TeaPotCodeInterceptor(List.of(HttpStatus.I_AM_A_TEAPOT,HttpStatus.UNAUTHORIZED));
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
