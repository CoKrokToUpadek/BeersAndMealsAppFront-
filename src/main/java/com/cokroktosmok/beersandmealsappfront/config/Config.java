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

    @Value("${beersandmeals.api.is_login_taken}")
    private String isLoginTaken;

    @Value("${beersandmeals.api.user_login}")
    private String login;

    @Value("${beersandmeals.api.get_users}")
    private String getUsers;

    @Value("${beersandmeals.api.set_user_role}")
    private String changeUserRole;

    @Value("${beersandmeals.api.set_user_status}")
    private String changeUserStatus;

    @Value("${beersandmeals.api.add_meal_to_favorites}")
    private String addToMealToFavorites;

    @Value("${beersandmeals.api.create_user}")
    private String createUser;

    @Value("${beersandmeals.api.get_favorite_beers}")
    private String getFavoriteBeers;

    @Value("${beersandmeals.api.get_favorite_meals}")
    private String getFavoriteMeals;

    @Value("${beersandmeals.api.add_favorite_beers}")
    private String addToFavoriteBeers;

    @Value("${beersandmeals.api.add_favorite_meals}")
    private String addToFavoriteMeals;

    @Value("${beersandmeals.api.remove_from_favorite_beers}")
    private String removeFromFavoriteBeers;

    @Value("${beersandmeals.api.remove_from_favorite_meals}")
    private String removeFromFavoriteMeals;

    @Value("${beersandmeals.api.update_beer_db}")
    private String updateBeerDb;

    @Value("${beersandmeals.api.update_meal_db}")
    private String updateMealDb;

    @Value("${beersandmeals.api.clear_beer_db}")
    private String clearBeerDb;

    @Value("${beersandmeals.api.clear_meal_db}")
    private String clearMealDb;


}
