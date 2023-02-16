package com.cokroktosmok.beersandmealsappfront.service;

import com.cokroktosmok.beersandmealsappfront.config.BackendCommunicationClient;
import com.cokroktosmok.beersandmealsappfront.data.dto.beer.BeerDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.MealDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BackEndDataManipulatorService {
    BackendCommunicationClient backendCommunicationClient;
    List<MealDto> mealDtoList;
    List<BeerDto> beerDtoList;


    @Autowired
    public BackEndDataManipulatorService(BackendCommunicationClient backendCommunicationClient) {
        this.backendCommunicationClient = backendCommunicationClient;
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


}
