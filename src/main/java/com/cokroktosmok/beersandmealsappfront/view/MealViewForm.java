package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.data.dto.meal.IngredientAndMeasureDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.MealDto;
import com.cokroktosmok.beersandmealsappfront.service.BackEndDataManipulatorService;
import com.cokroktosmok.beersandmealsappfront.static_recources.GraphicAssets;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;


import java.util.ArrayList;


public class MealViewForm extends FormLayout {
    MainView parentView;
    private MealDto mealDto;
    private final GraphicAssets graphicAssets = new GraphicAssets();
    private final String phVideo = "https://www.youtube.com/watch?v=NpEaa2P7qZI";
    Image thumbnail;
    //placeholder so that vaadin won't ignore this field
    IFrame embeddedPlayer = new IFrame();
    private final int embeddedHeight = 400;
    private final int embeddedWidth = 400;
    TextField name = new TextField("name");
    TextField category = new TextField("category");
    TextField area = new TextField("area");
    TextArea instruction = new TextArea("instruction");
    TextField tags = new TextField("tags");
    TextField youtubeLink = new TextField("youtubeLink");
    TextField source = new TextField("source");

    Button favoritesButton = new Button("add to favorites");
    Button closeButton = new Button("close");
    private final Grid<IngredientAndMeasureDto> ingredientAndMeasureDtoGrid = new Grid<>(IngredientAndMeasureDto.class);
    Binder<MealDto> binder = new BeanValidationBinder<>(MealDto.class);
    BackEndDataManipulatorService backEndDataManipulatorService;

    Button deleteButton=new Button();

    boolean isAdmin;
    public MealViewForm(BackEndDataManipulatorService backEndDataManipulatorService, MainView parent,boolean isAdmin) {
        this.isAdmin=isAdmin;
        this.parentView = parent;
        this.backEndDataManipulatorService = backEndDataManipulatorService;
        instruction.setMaxLength(5000);
        binder.bindInstanceFields(this);
        textFieldLock(true);
        //placeholders so that vaadin won't ignore this fields
        thumbnail = graphicAssets.imageConfig("ph", embeddedWidth, embeddedHeight);
        embeddedPlayerConfig(phVideo, embeddedWidth, embeddedHeight);
        add(thumbnail, name, category, area, instruction, tags, getIngredientAndMeasureDtoGrid(), embeddedPlayer, source, createButtonsLayout());
    }
    private void setButtonForRemovingRecipeFromDb(){
        deleteButton.setText("delete recipe from Db");
    }

    public void setButtonForAddingToFavorites() {
        favoritesButton.setText("add to favorites");
        favoritesButton.addClickListener(e -> {
            backEndDataManipulatorService.addMealToFavorites(mealDto.getName());
        });
    }

    public void setButtonForRemovingFromFavorites() {
        favoritesButton.setText("remove from favorites");
        favoritesButton.addClickListener(e -> {
            backEndDataManipulatorService.removeMealFromFavorites(mealDto.getName());
            parentView.setMealDtoGridValues(parentView.updateCurrentFavoriteMealList());
        });
    }

    private void textFieldLock(boolean lockValue) {
        name.setReadOnly(lockValue);
        category.setReadOnly(lockValue);
        area.setReadOnly(lockValue);
        instruction.setReadOnly(lockValue);
        tags.setReadOnly(lockValue);
        youtubeLink.setReadOnly(lockValue);
        source.setReadOnly(lockValue);
    }

    private void embeddedPlayerConfig(String ytLink, int width, int height) {
        //simple conversion for iframe
        String embedLink = ytLink.replace("/watch?v=", "/embed/");
        embeddedPlayer.setSrc(embedLink);
        embeddedPlayer.setHeight(height + "px");
        embeddedPlayer.setWidth(width + "px");
        embeddedPlayer.setAllow("accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture");
        embeddedPlayer.getElement().setAttribute("allowfullscreen", true);
        embeddedPlayer.getElement().setAttribute("frameborder", "0");
    }


    private HorizontalLayout createButtonsLayout() {
        favoritesButton.addClickShortcut(Key.ENTER);
        setButtonForAddingToFavorites();
        closeButton.addClickListener(e -> setVisible(false));
        closeButton.addClickShortcut(Key.ESCAPE);
        if (isAdmin){
            setButtonForRemovingRecipeFromDb();
            return new HorizontalLayout(favoritesButton, closeButton,deleteButton);
        }
        return new HorizontalLayout(favoritesButton, closeButton);
    }


    private Grid<IngredientAndMeasureDto> getIngredientAndMeasureDtoGrid() {
        return ingredientAndMeasureDtoGrid;
    }

    public void setMeal(MealDto meal) {
        this.mealDto = meal;
        if (meal != null) {
            if (meal.getThumbnail() != null) {
                thumbnail = graphicAssets.imageConfig(mealDto.getThumbnail(), embeddedWidth, embeddedHeight);
            }
            if (meal.getYoutubeLink() != null) {
                embeddedPlayerConfig(meal.getYoutubeLink(), embeddedWidth, embeddedHeight);
            }
        }
        binder.readBean(meal);
    }

    public void setIngredientsList() {
        ingredientAndMeasureDtoGrid.setItems(mealDto.getIngredientsAndMeasureDtoList());
    }

    public void clearIngredientsList() {
        ingredientAndMeasureDtoGrid.setItems(new ArrayList<>());
    }
}
//    // Events
//    public static abstract class MealFormEvent extends ComponentEvent<MealViewForm> {
//        private MealDto mealDto;
//
//        protected MealFormEvent(MealViewForm source, MealDto contact) {
//            super(source, false);
//            this.mealDto = contact;
//        }
//
//        public MealDto getMealDto() {
//            return mealDto;
//        }
//    }
//
////
////    public static class CloseEvent extends MealFormEvent {
////        CloseEvent(MealViewForm source) {
////            super(source, null);
////        }
////    }
////
////    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
////                                                                  ComponentEventListener<T> listener) {
////        return getEventBus().addListener(eventType, listener);
////    }
//}
