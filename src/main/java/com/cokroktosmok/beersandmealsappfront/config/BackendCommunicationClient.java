package com.cokroktosmok.beersandmealsappfront.config;

import com.cokroktosmok.beersandmealsappfront.data.dto.beer.BeerDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.MealDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.user.CreatedUserDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.user.UserCredentialsDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
public class BackendCommunicationClient {

    private final Config beerConfig;
    private final RestTemplate restTemplate;


    @Autowired
    public BackendCommunicationClient(Config beerConfig, RestTemplate restTemplate) {
        this.beerConfig = beerConfig;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> createUser(CreatedUserDto userDto) {
        URI url = buildUriForCreatingUser();
        try {
            return restTemplate.postForEntity(url, userDto, String.class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private HttpEntity<String> headers() {
        String holder = SecurityContextHolder.getContext().getAuthentication().getName();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TokenStorage.getToken(holder));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }

//    private HttpEntity<String> headersForPost(String argument) {
//        String holder = SecurityContextHolder.getContext().getAuthentication().getName();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(TokenStorage.getToken(holder));
//        HttpEntity<String> entity = new HttpEntity<>(argument,headers);
//        return entity;
//    }

    public ResponseEntity<Boolean> checkIfLoginIsTaken(String login) {
        URI url = buildUriForCheckIfLoginIsTaken(login);
        try {
            return restTemplate.getForEntity(url, Boolean.class);

        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<UserCredentialsDto> getUser(String login) {
        URI url = buildUriForLogin(login);
        UserCredentialsDto user = restTemplate.getForObject(url, UserCredentialsDto.class);
        return Optional.of(user);
    }

    public List<UserDto> getUserDtoList() {
        URI url = buildUriForAllUsers();
        HttpEntity<String> entity = headers();
        try {
            ResponseEntity<UserDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, UserDto[].class);
            return Arrays.asList(response.getBody());
        }catch (HttpClientErrorException e){
            return new ArrayList<>();
        }
    }

    public List<MealDto> getMealDtoList() {
        URI url = buildUriForAllMeals();
        HttpEntity<String> entity = headers();
        try {
            ResponseEntity<MealDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, MealDto[].class);
            return Arrays.asList(response.getBody());
        }catch (HttpClientErrorException e){
            return new ArrayList<>();/*TODO change behavior based on response type (list or String)*/
        }
    }
    //simplified for doing both db's at the same time
    public List<String> updateRecipesDb(){
        URI url0=buildUriForUpdatingBeerDb();
        URI url1=buildUriForUpdatingMealDb();
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response0 = restTemplate.exchange(url0, HttpMethod.POST, entity, String.class);
        ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.POST, entity, String.class);
        return Arrays.asList(response0.getBody(),response1.getBody());
    }

    public List<String> clearRecipesDb(){
        URI url0=buildUriForClearingBeerDb();
        URI url1=buildUriForClearingMealDb();
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response0 = restTemplate.exchange(url0, HttpMethod.DELETE, entity, String.class);
        ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.DELETE, entity, String.class);
        return Arrays.asList(response0.getBody(),response1.getBody());
    }




    public List<MealDto> getFavoriteMealDtoList(){
        URI url=buildUriForFavoriteMeals();
        HttpEntity<String> entity = headers();
        ResponseEntity<MealDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, MealDto[].class);
        return Arrays.asList(response.getBody());
    }

    public List<BeerDto> getFavoriteBeerDtoList(){
        URI url=buildUriForFavoriteBeers();
        HttpEntity<String> entity = headers();
        ResponseEntity<BeerDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, BeerDto[].class);
        return Arrays.asList(response.getBody());
    }

    public String addToFavoriteMealDtoList(String meal){
        URI url=buildUriForAddToFavoriteMeals(meal);
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    public String addToFavoriteBeerDtoList(String beer){
        URI url=buildUriForAddToFavoriteBeers(beer);
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    public String removeFromFavoriteMealDtoList(String meal){
        URI url=buildUriForRemoveFromFavoriteMeals(meal);
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        return response.getBody();
    }

    public String removeFromFavoriteBeerDtoList(String beer){
        URI url=buildUriForRemoveFromFavoriteBeers(beer);
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        return response.getBody();
    }





    public List<BeerDto> getBeerDtoList() {
        URI url = buildUriForAllBeers();
        HttpEntity<String> entity = headers();
        try {
            ResponseEntity<BeerDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, BeerDto[].class);
            return Arrays.asList(response.getBody());
        }catch (HttpClientErrorException e){
            return new ArrayList<>();/*TODO change behavior based on response type (list or String)*/
        }
    }

    public String changeUserRole(String login, String role) {
        URI url = buildUriForChangingUserRole(login,role);
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        return response.getBody();
    }

    public String changeUserStatus(String login, Integer status) {
        URI url = buildUriForChangingUserStatus(login,status);
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        return response.getBody();
    }
    public String deleteSingleBeerFromDb(String name) {
        URI url = buildUriForDeletingSingleBeerFromDb(name);
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        return response.getBody();
    }

    public String deleteSingleMealFromDb(String name) {
        URI url = buildUriForDeletingSingleMealFromDb(name);
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        return response.getBody();
    }

    private URI buildUriForDeletingSingleBeerFromDb(String name) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getAdminFunctionalities())
                .pathSegment(beerConfig.getDeleteSingleBeerFromDb())
                .queryParam("beerName",name)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForDeletingSingleMealFromDb(String name) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getAdminFunctionalities())
                .pathSegment(beerConfig.getDeleteSingleMealFromDb())
                .queryParam("mealName",name)
                .build()
                .encode()
                .toUri();
    }


    private URI buildUriForChangingUserStatus(String login,Integer status) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getAdminFunctionalities())
                .pathSegment(beerConfig.getChangeUserStatus())
                .queryParam("login",login)
                .queryParam("status",status)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForChangingUserRole(String login,String role) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getAdminFunctionalities())
                .pathSegment(beerConfig.getChangeUserRole())
                .queryParam("login",login)
                .queryParam("role",role)
                .build()
                .encode()
                .toUri();
    }


    private URI buildUriForAllBeers() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getGetBeers())
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForAllMeals() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getGetMeals())
                .build()
                .encode()
                .toUri();
    }


    private URI buildUriForCheckIfLoginIsTaken(String login) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getIsLoginTaken())
                .queryParam("login", login)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForLogin(String login) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getLogin())
                .queryParam("login", login)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForAllUsers() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getAdminFunctionalities())
                .pathSegment(beerConfig.getGetUsers())
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForCreatingUser() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getCreateUser())
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForFavoriteMeals() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getGetFavoriteMeals())
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForFavoriteBeers() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getGetFavoriteBeers())
                .build()
                .encode()
                .toUri();
    }


    private URI buildUriForAddToFavoriteMeals(String meal) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getAddToFavoriteMeals())
                .queryParam("mealName",meal)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForAddToFavoriteBeers(String beer) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getAddToFavoriteBeers())
                .queryParam("beerName",beer)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForRemoveFromFavoriteMeals(String meal) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getRemoveFromFavoriteMeals())
                .queryParam("mealName",meal)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForRemoveFromFavoriteBeers(String beer) {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getRemoveFromFavoriteBeers())
                .queryParam("beerName",beer)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForClearingBeerDb() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getAdminFunctionalities())
                .pathSegment(beerConfig.getClearBeerDb())
                .build()
                .encode()
                .toUri();
    }
    private URI buildUriForClearingMealDb() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getAdminFunctionalities())
                .pathSegment(beerConfig.getClearMealDb())
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForUpdatingBeerDb() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getAdminFunctionalities())
                .pathSegment(beerConfig.getUpdateBeerDb())
                .build()
                .encode()
                .toUri();
    }
    private URI buildUriForUpdatingMealDb() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getAdminFunctionalities())
                .pathSegment(beerConfig.getUpdateMealDb())
                .build()
                .encode()
                .toUri();
    }



}
