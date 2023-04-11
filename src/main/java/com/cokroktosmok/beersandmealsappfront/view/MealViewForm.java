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
import com.vaadin.flow.shared.Registration;


import java.util.ArrayList;


public class MealViewForm extends FormLayout {
    MainView parentView;
    private MealDto mealDto;
    private final GraphicAssets graphicAssets = new GraphicAssets();
    //placeholder video for when there is no video related to record in db
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

    DialogWindow dialogForButtonActions;
    //this convoluted way to manipulate eventClickListeners, but it works only this way
    Registration registration;

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
        HorizontalLayout imageContainer=new HorizontalLayout();
        imageContainer.add(thumbnail);
        imageContainer.addClassName("image-formatter");
        imageContainer.addClassName("padding-60");
        add(imageContainer, name, category, area, instruction, tags, getIngredientAndMeasureDtoGrid(), embeddedPlayer, source, createButtonsLayout());
    }
    private void setButtonForRemovingRecipeFromDb(){
        deleteButton.setText("delete recipe from Db");
        deleteButton.addClickListener(e->{
            String msg= backEndDataManipulatorService.deleteSingleMealFromDb(mealDto.getName());
            parentView.updateMealList();
            dialogForButtonActions=new DialogWindow("removing meal recipe from db",msg);
            dialogForButtonActions.getDialog().open();
        });
    }

    public void setButtonForAddingToFavorites() {
        if (registration!=null){
            registration.remove();
        }
        favoritesButton.setText("add to favorites");
        registration =    favoritesButton.addClickListener(e -> eventListenerForAddingToFavorites());
    }

    public void setButtonForRemovingFromFavorites() {
        if (registration!=null){
            registration.remove();
        }
        favoritesButton.setText("remove from favorites");
        registration = favoritesButton.addClickListener(e -> eventListenerForRemovingFromFavorites());
    }

    private void eventListenerForAddingToFavorites(){
        String msg=   backEndDataManipulatorService.addMealToFavorites(mealDto.getName());
        dialogForButtonActions=new DialogWindow("adding meal to favorites",msg);
        dialogForButtonActions.getDialog().open();
    }

    private void eventListenerForRemovingFromFavorites(){
        String msg=  backEndDataManipulatorService.removeMealFromFavorites(mealDto.getName());
        parentView.setMealDtoGridValues(parentView.updateCurrentFavoriteMealList());
        dialogForButtonActions=new DialogWindow("removing meal from favorites",msg);
        dialogForButtonActions.getDialog().open();
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

    public void setMealViewContent(MealDto mealDto){
        setMeal(mealDto);
        setIngredientsList();
        setVisible(true);
    }

    public void clearMealViewContent(){
        clearIngredientsList();
        setMeal(null);
        setVisible(false);
    }

    private void setMeal(MealDto meal) {
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

    private void setIngredientsList() {
        ingredientAndMeasureDtoGrid.setItems(mealDto.getIngredientsAndMeasureDtoList());
    }

    private void clearIngredientsList() {
        ingredientAndMeasureDtoGrid.setItems(new ArrayList<>());
    }
}

