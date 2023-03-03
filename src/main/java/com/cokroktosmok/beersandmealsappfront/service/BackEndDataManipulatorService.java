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
    //do naprawy
    public void addMealToFavorites(String mealName) {
        backendCommunicationClient.addToFavoriteMealDtoList(mealName);
    }

    public void addBeerToFavorites(String beerName) {
        backendCommunicationClient.addToFavoriteBeerDtoList(beerName);
    }

    public void removeMealFromFavorites(String mealName) {
        backendCommunicationClient.removeFromFavoriteMealDtoList(mealName);
    }

    public void removeBeerFromFavorites(String beerName) {
        backendCommunicationClient.removeFromFavoriteBeerDtoList(beerName);
    }

    public List<UserDto> findAllUsers(String stringFilter){
        userDtoList = backendCommunicationClient.getUserDtoList();
        if (stringFilter == null || stringFilter.isEmpty()) {
            return userDtoList;
        } else {
            return userDtoList.stream().filter(e -> e.getLogin().toLowerCase().contains(stringFilter.toLowerCase())).collect(Collectors.toList());
        }
    }

    public void setUserRole(String login, String role){
        String response=backendCommunicationClient.changeUserRole(login,role);
    }

    public void setUserStatus(String login, Integer status){
        String response=backendCommunicationClient.changeUserStatus(login,status);
    }

}
