package com.cokroktosmok.beersandmealsappfront.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Config {

    @Value("${beersandmeals.api.base_endpoint}")
    private String beerAppBasicEndpoint;

    @Value("${beersandmeals.api.users_functionalities}")
    private String userFunctionalities;

    @Value("${beersandmeals.api.admin_functionalities}")
    private String adminFunctionalities;

    @Value("${beersandmeals.api.get_meals}")
    private String getMeals;

    @Value("${beersandmeals.api.get_beers}")
    private String getBeers;

    @Value("${beersandmeals.api.user_login}")
    private String login;

    @Value("${beersandmeals.api.add_meal_to_favorites}")
    private String addToMealToFavorites;


}
