package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.data.dto.meal.IngredientAndMeasureDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.MealDto;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.ArrayList;


public class MealViewPopUp extends FormLayout {
    private MealDto mealDto;
    TextField name = new TextField("name");
    TextField category = new TextField("category");
    TextField area = new TextField("area");
    TextArea instruction = new TextArea("instruction");
    TextField thumbnail = new TextField("thumbnail");
    TextField tags = new TextField("tags");
    TextField youtubeLink = new TextField("youtubeLink");
    TextField source = new TextField("source");
    Button favoritesButton = new Button("Favorites");
    Button closeButton = new Button("close");
    private final Grid<IngredientAndMeasureDto> ingredientAndMeasureDtoGrid = new Grid<>(IngredientAndMeasureDto.class);
    Binder<MealDto> binder = new BeanValidationBinder<>(MealDto.class);
    public MealViewPopUp() {
        instruction.setMaxLength(5000);
        binder.bindInstanceFields(this);
        add(name,category,area, instruction,thumbnail,tags,youtubeLink,configureGrid(),source,createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        favoritesButton.addClickShortcut(Key.ENTER);
        closeButton.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(favoritesButton, closeButton);
    }

    private Grid<IngredientAndMeasureDto> configureGrid(){
                return ingredientAndMeasureDtoGrid;
    }
    public void setMeal(MealDto meal) {
        this.mealDto = meal;
        binder.readBean(meal);
    }

    public void setIngredientsList(){
        ingredientAndMeasureDtoGrid.setItems(mealDto.getIngredientsAndMeasureDtoList());
    }
    public void clearIngredientsList(){
        ingredientAndMeasureDtoGrid.setItems(new ArrayList<>());
    }
//    // Events
//    public static abstract class MealFormEvent extends ComponentEvent<MealViewPopUp> {
//        private MealDto mealDto;
//
//        protected MealFormEvent(MealViewPopUp source, MealDto contact) {
//            super(source, false);
//            this.mealDto = contact;
//        }
//
//        public MealDto getMealDto() {
//            return mealDto;
//        }
//    }
//
//
//    public static class CloseEvent extends MealFormEvent {
//        CloseEvent(MealViewPopUp source) {
//            super(source, null);
//        }
//    }
//
//    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
//                                                                  ComponentEventListener<T> listener) {
//        return getEventBus().addListener(eventType, listener);
//    }
}
