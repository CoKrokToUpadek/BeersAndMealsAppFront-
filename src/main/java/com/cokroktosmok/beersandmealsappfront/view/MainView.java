package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.config.BackendCommunicationClient;
import com.cokroktosmok.beersandmealsappfront.data.dto.beer.BeerDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.MealDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "")
@PageTitle("BeersAndMeal")
public class MainView extends HorizontalLayout {
    private Grid<MealDto> mealDtoGrid = new Grid<>(MealDto.class);
    private Grid<BeerDto> beerDtoGrid=new Grid<>(BeerDto.class);

    @Autowired
    public MainView(BackendCommunicationClient backendCommunicationClient) {
        mealDtoGrid.setColumns("name","category");
        beerDtoGrid.setColumns("name");
        add(mealDtoGrid);
        add(beerDtoGrid);
        List<MealDto> mealDtoList= backendCommunicationClient.getMealDtoList();
        List<BeerDto> beerDtoList=backendCommunicationClient.getBeerDtoList();
        setSizeFull();
        mealDtoGrid.setItems(mealDtoList);
        beerDtoGrid.setItems(beerDtoList);
    }

}