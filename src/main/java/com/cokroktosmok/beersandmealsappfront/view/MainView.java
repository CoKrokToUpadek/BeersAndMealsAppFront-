package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.data.dto.beer.BeerDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.MealDto;

import com.cokroktosmok.beersandmealsappfront.service.BackEndDataManipulatorService;
import com.cokroktosmok.beersandmealsappfront.static_recources.GraphicAssets;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.annotation.security.PermitAll;
import java.util.List;

@Route(value = "/")
@PageTitle("BeersAndMeal")
@PermitAll
@CssImport("./styles/styles.css")
public class MainView extends VerticalLayout {

    private final Grid<MealDto> mealDtoGrid = new Grid<>(MealDto.class);
    private final Grid<BeerDto> beerDtoGrid=new Grid<>(BeerDto.class);
    TextField filterBeerText = new TextField();
    TextField filterMealText = new TextField();

    MealViewForm mealViewForm;

    BeerViewForm beerViewForm;

    BackEndDataManipulatorService backEndDataManipulatorService;

    @Autowired
    public MainView(BackEndDataManipulatorService backEndDataManipulatorService) {
        this.backEndDataManipulatorService=backEndDataManipulatorService;
        this.mealViewForm = new MealViewForm(this.backEndDataManipulatorService);
        this.beerViewForm=new BeerViewForm(this.backEndDataManipulatorService);
        add(buttons());
        add(getGridsLayout());
        configureMealViewForm();
        configureBeerViewForm();
        setSizeFull();
        mealViewForm.setVisible(false);
        beerViewForm.setVisible(false);
    }



    private HorizontalLayout getToolbar(TextField textField) {
        textField.setPlaceholder("Filter by name...");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        HorizontalLayout toolbar = new HorizontalLayout(textField);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private HorizontalLayout buttons(){
        Button logout = new Button("Log out",e-> UI.getCurrent().navigate("/login"));
        Button favorites = new Button("favorites lists",e->setFavoriteLists());
        Button defaultList = new Button("beers and meals lists",e->setDefaultLists());
        return new HorizontalLayout(logout,defaultList,favorites);
    }

    private HorizontalLayout getGridsLayout(){
        HorizontalLayout grids= new HorizontalLayout();
        grids.add(beerViewForm);
        grids.add(getBeerLayout(backEndDataManipulatorService));
        grids.add(getMealsLayout(backEndDataManipulatorService));
        grids.add(mealViewForm);
        grids.setSizeFull();
        return grids;
    }


    private VerticalLayout getMealsLayout(BackEndDataManipulatorService backEndDataManipulatorService){
        VerticalLayout mealsLayout=new VerticalLayout();
        filterMealText.addValueChangeListener(e-> updateMealList());
        mealsLayout.add(getToolbar(filterMealText));
        mealDtoGrid.setColumns("name");
        mealDtoGrid.setAllRowsVisible(false);
        mealsLayout.add(mealDtoGrid);
        List<MealDto> mealDtoList= backEndDataManipulatorService.findAllMeals(null);
        mealDtoGrid.setItems(mealDtoList);
        mealDtoGrid.asSingleSelect().addValueChangeListener(event -> editMeal(event.getValue()));
        return mealsLayout;
    }

    private VerticalLayout getBeerLayout(BackEndDataManipulatorService backEndDataManipulatorService){
        VerticalLayout beersLayout=new VerticalLayout();
        filterBeerText.addValueChangeListener(e-> updateBeerList());
        beersLayout.add(getToolbar(filterBeerText));
        beerDtoGrid.setColumns("name");
        beerDtoGrid.setAllRowsVisible(false);
        beersLayout.add(beerDtoGrid);
        List<BeerDto> beerDtoList=backEndDataManipulatorService.findAllBeers(null);
        beerDtoGrid.setItems(beerDtoList);
        beerDtoGrid.asSingleSelect().addValueChangeListener(event -> editBeer(event.getValue()));
        return beersLayout;
    }


    private void setDefaultLists(){
        beerDtoGrid.setItems(backEndDataManipulatorService.findAllBeers(null));
        mealDtoGrid.setItems(backEndDataManipulatorService.findAllMeals(null));
    }



    private void setFavoriteLists(){
        beerDtoGrid.setItems(backEndDataManipulatorService.findFavoriteBeers(null));
        mealDtoGrid.setItems(backEndDataManipulatorService.findFavoriteMeals(null));
    }

    private void configureMealViewForm(){
        mealViewForm.setWidth("80em");
    }
    public void editMeal(MealDto mealDto) {
        if (mealDto == null) {
            closeMealEditor();
        } else {
            mealViewForm.setMeal(mealDto);
            mealViewForm.setIngredientsList();
            mealViewForm.setVisible(true);
        }
    }

    private void configureBeerViewForm(){
        beerViewForm.setWidth("80em");
    }
    public void editBeer(BeerDto beerDto) {
        if (beerDto == null) {
            closeBeerEditor();
        } else {
            beerViewForm.setBeer(beerDto);
            beerViewForm.volumeAccordionSetVolume();
            beerViewForm.boilVolumeAccordionSetVolume();
            beerViewForm.setHopsGrid();
            beerViewForm.setMaltsGrid();
            beerViewForm.setFoodPairingsGrid();
            beerViewForm.setVisible(true);
        }
    }

    private void updateBeerList() {
        beerDtoGrid.setItems(backEndDataManipulatorService.findAllBeers(filterBeerText.getValue()));
    }


    private void updateMealList() {
        mealDtoGrid.setItems(backEndDataManipulatorService.findAllMeals(filterMealText.getValue()));
    }

    private void closeMealEditor() {
        mealViewForm.clearIngredientsList();
        mealViewForm.setMeal(null);
        mealViewForm.setVisible(false);
    }

    private void closeBeerEditor() {
        beerViewForm.volumeAccordionClearVolume();
        beerViewForm.boilVolumeAccordionClearVolume();
        beerViewForm.setBeer(null);
        beerViewForm.setVisible(false);
    }

}