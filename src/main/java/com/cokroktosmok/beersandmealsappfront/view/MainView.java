package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.data.dto.beer.BeerDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.MealDto;
import com.cokroktosmok.beersandmealsappfront.security.SecurityService;
import com.cokroktosmok.beersandmealsappfront.service.BackEndDataManipulatorService;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Route(value = "/")
@PageTitle("BeersAndMeal")
public class MainView extends VerticalLayout {
    private final Grid<MealDto> mealDtoGrid = new Grid<>(MealDto.class);
    private final Grid<BeerDto> beerDtoGrid=new Grid<>(BeerDto.class);
    TextField filterBeerText = new TextField();
    TextField filterMealText = new TextField();

    MealViewForm mealViewForm = new MealViewForm();

    BeerViewForm beerViewForm =new BeerViewForm();
    SecurityService securityService;
    BackEndDataManipulatorService backEndDataManipulatorService;

    Accordion volumeAccordion=new Accordion();


    @Autowired
    public MainView(SecurityService securityService,BackEndDataManipulatorService backEndDataManipulatorService) {

        this.securityService=securityService;
        this.backEndDataManipulatorService=backEndDataManipulatorService;
        add(buttons());
        add(getGridsLayout(backEndDataManipulatorService));
        configureMealViewForm();
        configureBeerViewForm();
        setSizeFull();
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
        Button logout = new Button("Log out", e -> securityService.logout());
        return new HorizontalLayout(logout);
    }

    private HorizontalLayout getGridsLayout(BackEndDataManipulatorService backEndDataManipulatorService){
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