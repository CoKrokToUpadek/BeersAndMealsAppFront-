package com.cokroktosmok.beersandmealsappfront.config;

import com.cokroktosmok.beersandmealsappfront.data.dto.beer.BeerDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.MealDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.user.CreatedUserDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.user.UserCredentialsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BackendCommunicationClient {

    private final Config beerConfig;
    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;
    public String createUser(CreatedUserDto userDto) throws JsonProcessingException {
        URI url =buildUriForCreatingUser();
        return restTemplate.postForObject(url,userDto, String.class);

    }

    public Boolean checkIfLoginIsTaken(String login){
        URI url=buildUriForCheckIfLoginIsTaken(login);
        return restTemplate.getForObject(url,Boolean.class);
    }

    public ResponseEntity <String> addMealToFavorites(String mealName){
        URI url=buildUriForAddingMealToFavorites(mealName);
        return restTemplate.getForObject(url, ResponseEntity.class);
    }


    public Optional<UserCredentialsDto> getUser(String login){
        URI url=buildUriForLogin(login);
        UserCredentialsDto user=restTemplate.getForObject(url,UserCredentialsDto.class);
        return Optional.of(user);
    }

    public List<MealDto> getMealDtoList() {
        URI url = buildUriForAllMeals();
        MealDto[] mealDtoList = restTemplate.getForObject(url, MealDto[].class);
        return Optional.of(mealDtoList).map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    private URI buildUriForAllMeals() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getGetMeals())
                .build()
                .encode()
                .toUri();
    }


    public List<BeerDto> getBeerDtoList() {
        URI url = buildUriForAllBeers();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("user2222","user"));
        BeerDto[] beerDtoList = restTemplate.getForObject(url, BeerDto[].class);
        return Optional.of(beerDtoList).map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    private URI buildUriForAllBeers() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getGetBeers())
                .build()
                .encode()
                .toUri();
    }


    private URI buildUriForCheckIfLoginIsTaken(String login) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getIsLoginTaken())
                .queryParam("login",login)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForLogin(String login) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getLogin())
                .queryParam("login",login)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForAddingMealToFavorites(String meal) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getAddToMealToFavorites())
                .queryParam("mealName",meal)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForCreatingUser(){
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getCreateUser())
                .build()
                .encode()
                .toUri();
    }

}
