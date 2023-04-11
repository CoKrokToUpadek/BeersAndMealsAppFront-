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
        URI url =  universalUriBuilder(beerConfig.getUserFunctionalities(),beerConfig.getCreateUser());
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


    public ResponseEntity<Boolean> checkIfLoginIsTaken(String login) {
        URI url =  universalUriBuilder(beerConfig.getUserFunctionalities(),beerConfig.getIsLoginTaken(),new ParamSetClass("login", login));
        try {
            return restTemplate.getForEntity(url, Boolean.class);

        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<UserCredentialsDto> getUser(String login) {
        URI url =  universalUriBuilder(beerConfig.getUserFunctionalities(),beerConfig.getLogin(),new ParamSetClass("login", login));
        UserCredentialsDto user = restTemplate.getForObject(url, UserCredentialsDto.class);
        return Optional.of(user);
    }

    public List<UserDto> getUserDtoList() {
        URI url =  universalUriBuilder(beerConfig.getAdminFunctionalities(),beerConfig.getGetUsers());
        HttpEntity<String> entity = headers();
        try {
            ResponseEntity<UserDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, UserDto[].class);
            return Arrays.asList(response.getBody());
        }catch (HttpClientErrorException e){
            return new ArrayList<>();
        }
    }

    public List<MealDto> getMealDtoList() {
        URI url =  universalUriBuilder(beerConfig.getUserFunctionalities(),beerConfig.getGetMeals());
        HttpEntity<String> entity = headers();
        try {
            ResponseEntity<MealDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, MealDto[].class);
            return Arrays.asList(response.getBody());
        }catch (HttpClientErrorException e){
            return new ArrayList<>();
        }
    }
    //simplified for doing both db's at the same time
    public List<String> updateRecipesDb(){
        URI url0= universalUriBuilder(beerConfig.getAdminFunctionalities(),beerConfig.getUpdateBeerDb());
        URI url1=universalUriBuilder(beerConfig.getAdminFunctionalities(),beerConfig.getUpdateMealDb());
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response0 = restTemplate.exchange(url0, HttpMethod.POST, entity, String.class);
        ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.POST, entity, String.class);
        return Arrays.asList(response0.getBody(),response1.getBody());
    }

    public List<String> clearRecipesDb(){
        URI url0= universalUriBuilder(beerConfig.getAdminFunctionalities(),beerConfig.getClearBeerDb());
        URI url1=universalUriBuilder(beerConfig.getAdminFunctionalities(),beerConfig.getClearMealDb());

        HttpEntity<String> entity = headers();
        ResponseEntity<String> response0 = restTemplate.exchange(url0, HttpMethod.DELETE, entity, String.class);
        ResponseEntity<String> response1 = restTemplate.exchange(url1, HttpMethod.DELETE, entity, String.class);
        return Arrays.asList(response0.getBody(),response1.getBody());
    }




    public List<MealDto> getFavoriteMealDtoList(){
        URI url =  universalUriBuilder(beerConfig.getUserFunctionalities(),beerConfig.getGetFavoriteMeals());
        HttpEntity<String> entity = headers();
        ResponseEntity<MealDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, MealDto[].class);
        return Arrays.asList(response.getBody());
    }

    public List<BeerDto> getFavoriteBeerDtoList(){
        URI url =  universalUriBuilder(beerConfig.getUserFunctionalities(),beerConfig.getGetFavoriteBeers());
        HttpEntity<String> entity = headers();
        ResponseEntity<BeerDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, BeerDto[].class);
        return Arrays.asList(response.getBody());
    }

    public String addToFavoriteMealDtoList(String meal){
        URI url =  universalUriBuilder(beerConfig.getUserFunctionalities(),beerConfig.getAddToMealToFavorites(),new ParamSetClass("mealName", meal));
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    public String addToFavoriteBeerDtoList(String beer){
        URI url =  universalUriBuilder(beerConfig.getUserFunctionalities(),beerConfig.getAddToFavoriteBeers(),new ParamSetClass("beerName", beer));
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    public String removeFromFavoriteMealDtoList(String meal){
        URI url =  universalUriBuilder(beerConfig.getUserFunctionalities(),beerConfig.getRemoveFromFavoriteMeals(),new ParamSetClass("mealName", meal));
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        return response.getBody();
    }

    public String removeFromFavoriteBeerDtoList(String beer){
        URI url =  universalUriBuilder(beerConfig.getUserFunctionalities(),beerConfig.getRemoveFromFavoriteBeers(),new ParamSetClass("beerName", beer));
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        return response.getBody();
    }


    public List<BeerDto> getBeerDtoList() {
        URI url =  universalUriBuilder(beerConfig.getUserFunctionalities(),beerConfig.getGetBeers());
        HttpEntity<String> entity = headers();
        try {
            ResponseEntity<BeerDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, BeerDto[].class);
            return Arrays.asList(response.getBody());
        }catch (HttpClientErrorException e){
            return new ArrayList<>();
        }
    }

    public String changeUserRole(String login, String role) {
        URI url =  universalUriBuilder(beerConfig.getAdminFunctionalities(),beerConfig.getChangeUserRole(),new ParamSetClass("login", login),new ParamSetClass("role", role));
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        return response.getBody();
    }

    public String changeUserStatus(String login, Integer status) {
        URI url = universalUriBuilder(beerConfig.getAdminFunctionalities(),beerConfig.getChangeUserStatus(),new ParamSetClass("login", login),new ParamSetClass("status", status));
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        return response.getBody();
    }
    public String deleteSingleBeerFromDb(String name) {
        URI url = universalUriBuilder(beerConfig.getAdminFunctionalities(),beerConfig.deleteSingleBeerFromDb,new ParamSetClass("beerName", name));
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        return response.getBody();
    }

    public String deleteSingleMealFromDb(String name) {
        URI url = universalUriBuilder(beerConfig.getAdminFunctionalities(),beerConfig.deleteSingleMealFromDb,new ParamSetClass("mealName", name));
        HttpEntity<String> entity = headers();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        return response.getBody();
    }





    private URI universalUriBuilder(String functionalitiesLevel, String endpointPath, ParamSetClass... params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(functionalitiesLevel)
                .pathSegment(endpointPath);
        for (ParamSetClass param : params) {
            builder.queryParam(param.getParamName(), param.getParamArg());
        }
        return builder.build()
                .encode()
                .toUri();
    }

    private class ParamSetClass {
       private final String paramName;
      private final Object paramArg;

        public ParamSetClass(String paramName, Object paramArg) {
            this.paramName = paramName;
            this.paramArg = paramArg;
        }

        public String getParamName() {
            return paramName;
        }

        public Object getParamArg() {
            return paramArg;
        }
    }
}
