package com.cokroktosmok.beersandmealsappfront.service;

import com.cokroktosmok.beersandmealsappfront.config.BackendCommunicationClient;
import com.cokroktosmok.beersandmealsappfront.data.dto.beer.BeerDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.MealDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BackEndDataManipulatorService {
  private   BackendCommunicationClient backendCommunicationClient;
   private List<MealDto> mealDtoList;
    private   List<BeerDto> beerDtoList;

    private List<UserDto> userDtoList;


    @Autowired
    public BackEndDataManipulatorService(BackendCommunicationClient backendCommunicationClient) {
        this.backendCommunicationClient = backendCommunicationClient;
    }

    public List<String> updateRecipesDb(){
        return backendCommunicationClient.updateRecipesDb();
    }
    public List<String> clearRecipesDb(){
        return backendCommunicationClient.clearRecipesDb();
    }

    public List<MealDto> findAllMeals(String stringFilter) {
        mealDtoList = backendCommunicationClient.getMealDtoList();
        if (stringFilter == null || stringFilter.isEmpty()) {
            return mealDtoList;
        } else {
            return mealDtoList.stream().filter(e -> e.getName().toLowerCase().contains(stringFilter.toLowerCase())).collect(Collectors.toList());
        }
    }


    public List<BeerDto> findAllBeers(String stringFilter) {
        beerDtoList = backendCommunicationClient.getBeerDtoList();
        if (stringFilter == null || stringFilter.isEmpty()) {
            return beerDtoList;
        } else {
            return beerDtoList.stream().filter(e -> e.getName().toLowerCase().contains(stringFilter.toLowerCase())).collect(Collectors.toList());
        }
    }

    public List<MealDto> findFavoriteMeals(String stringFilter) {
        mealDtoList = backendCommunicationClient.getFavoriteMealDtoList();
        if (stringFilter == null || stringFilter.isEmpty()) {
            return mealDtoList;
        } else {
            return mealDtoList.stream().filter(e -> e.getName().toLowerCase().contains(stringFilter.toLowerCase())).collect(Collectors.toList());
        }

    }

    public List<BeerDto> findFavoriteBeers(String stringFilter) {
        beerDtoList = backendCommunicationClient.getFavoriteBeerDtoList();
        if (stringFilter == null || stringFilter.isEmpty()) {
            return beerDtoList;
        } else {
            return beerDtoList.stream().filter(e -> e.getName().toLowerCase().contains(stringFilter.toLowerCase())).collect(Collectors.toList());
        }
    }

    public String addMealToFavorites(String mealName) {
      return   backendCommunicationClient.addToFavoriteMealDtoList(mealName);
    }

    public String addBeerToFavorites(String beerName) {
       return backendCommunicationClient.addToFavoriteBeerDtoList(beerName);
    }

    public String removeMealFromFavorites(String mealName) {
       return backendCommunicationClient.removeFromFavoriteMealDtoList(mealName);
    }

    public String removeBeerFromFavorites(String beerName) {
      return   backendCommunicationClient.removeFromFavoriteBeerDtoList(beerName);
    }

    public List<UserDto> findAllUsers(String stringFilter){
        userDtoList = backendCommunicationClient.getUserDtoList();
        if (stringFilter == null || stringFilter.isEmpty()) {
            return userDtoList;
        } else {
            return userDtoList.stream().filter(e -> e.getLogin().toLowerCase().contains(stringFilter.toLowerCase())).collect(Collectors.toList());
        }
    }

    public String setUserRole(String login, String role){
        return backendCommunicationClient.changeUserRole(login,role);
    }

    public String setUserStatus(String login, Integer status){
        return backendCommunicationClient.changeUserStatus(login,status);
    }

    public String deleteSingleBeerFromDb(String name) {
        return backendCommunicationClient.deleteSingleBeerFromDb(name);
    }
    public String deleteSingleMealFromDb(String name) {
        return backendCommunicationClient.deleteSingleMealFromDb(name);
    }
}
