package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.config.BackendCommunicationClient;
import com.cokroktosmok.beersandmealsappfront.data.dto.beer.BeerDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.MealDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "")
@PageTitle("BeersAndMeal")
public class MainView extends VerticalLayout {
    private final Grid<MealDto> mealDtoGrid = new Grid<>(MealDto.class);
    private final Grid<BeerDto> beerDtoGrid=new Grid<>(BeerDto.class);
    TextField filterBeerText = new TextField();
    TextField filterMealText = new TextField();
    MealViewPopUp mealViewPopUp= new MealViewPopUp();

    @Autowired
    public MainView(BackendCommunicationClient backendCommunicationClient) {

        add(getGridsLayout(backendCommunicationClient));
        configureMealViewPopUp();
        setSizeFull();
    }

    private HorizontalLayout getToolbar(TextField textField) {
        textField.setPlaceholder("Filter by name...");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        Button addContactButton = new Button("search");
        HorizontalLayout toolbar = new HorizontalLayout(textField, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private HorizontalLayout getGridsLayout(BackendCommunicationClient backendCommunicationClient){
        HorizontalLayout grids= new HorizontalLayout();
        grids.add(getBeerLayout(backendCommunicationClient));
        grids.add(getMealsLayout(backendCommunicationClient));
        grids.add(mealViewPopUp);
        grids.setSizeFull();
        return grids;
    }


    private VerticalLayout getMealsLayout(BackendCommunicationClient backendCommunicationClient){
        VerticalLayout mealsLayout=new VerticalLayout();
        mealsLayout.add(getToolbar(filterMealText));
        mealDtoGrid.setColumns("name","category");
        mealsLayout.add(mealDtoGrid);
        List<MealDto> mealDtoList= backendCommunicationClient.getMealDtoList();
        mealDtoGrid.setItems(mealDtoList);
        //test
        mealDtoGrid.asSingleSelect().addValueChangeListener(event ->
                editMeal(event.getValue()));
        //
        return mealsLayout;
    }

    private VerticalLayout getBeerLayout(BackendCommunicationClient backendCommunicationClient){
        VerticalLayout beersLayout=new VerticalLayout();
        beersLayout.add(getToolbar(filterBeerText));
        beerDtoGrid.setColumns("name");
        beersLayout.add(beerDtoGrid);
        List<BeerDto> beerDtoList=backendCommunicationClient.getBeerDtoList();
        beerDtoGrid.setItems(beerDtoList);
        return beersLayout;
    }

    private void configureMealViewPopUp(){
        mealViewPopUp.setWidth("40em");
    }
    public void editMeal(MealDto mealDto) {
        if (mealDto == null) {
            closeEditor();
        } else {
            mealViewPopUp.setMeal(mealDto);
            mealViewPopUp.setIngredientsList();
            mealViewPopUp.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        mealViewPopUp.clearIngredientsList();
        mealViewPopUp.setMeal(null);
        mealViewPopUp.setVisible(false);
        removeClassName("editing");
    }

}