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
        mealDtoList=backendCommunicationClient.getMealDtoList();
        if (stringFilter == null || stringFilter.isEmpty()) {
            return mealDtoList;
        } else {
            return mealDtoList.stream().filter(e->e.getName().toLowerCase().contains(stringFilter.toLowerCase())).collect(Collectors.toList());
        }
    }


    public List<BeerDto> findAllBeers(String stringFilter) {
        beerDtoList=backendCommunicationClient.getBeerDtoList();
        if (stringFilter == null || stringFilter.isEmpty()) {
            return beerDtoList;
        } else {
            return beerDtoList.stream().filter(e->e.getName().toLowerCase().contains(stringFilter.toLowerCase())).collect(Collectors.toList());
        }
    }

    public void addMealToFavorites(String mealName){
        backendCommunicationClient.addMealToFavorites(mealName);
    }


}
