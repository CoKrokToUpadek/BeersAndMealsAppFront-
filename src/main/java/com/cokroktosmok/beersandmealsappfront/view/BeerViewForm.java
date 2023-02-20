package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.data.dto.beer.*;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.IngredientAndMeasureDto;
import com.cokroktosmok.beersandmealsappfront.service.BackEndDataManipulatorService;
import com.cokroktosmok.beersandmealsappfront.static_recources.GraphicAssets;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BeerViewForm extends FormLayout {

    private MainView parentView;
    private final int embeddedHeight = 400;
    private final int embeddedWidth = 200;
    private final GraphicAssets graphicAssets = new GraphicAssets();
    private BeerDto beerDto;
    TextField name = new TextField("name");
    TextArea description = new TextArea("description");
    Image imageUrl;
    TextField abv = new TextField("abv");
    TextField ibu = new TextField("ibu");
    TextField target_og = new TextField("target_og");
    TextField ebc = new TextField("ebc");
    TextField srm = new TextField("srm");
    TextField attenuationLevel = new TextField("attenuationLevel");

    Accordion volume = new Accordion();

    Accordion boilVolume = new Accordion();

    TextArea brewers_tips = new TextArea("brewers_tips");
    TextField contributed_by = new TextField("contributed_by");
    Button favoritesButton = new Button("favorites");//placeholder
    Button closeButton = new Button("close");

    Span maltName=new Span("Malts list");

    Span hopsName=new Span("Hops list");

    TextField yeast=new TextField("Yeast");

    Button deleteButton=new Button();

    HorizontalLayout volumeHorizontalLayout = new HorizontalLayout();

    HorizontalLayout boilVolumeHorizontalLayout = new HorizontalLayout();

    private final Grid<MaltsForInterfaceDto> maltsGrid = new Grid<>(MaltsForInterfaceDto.class);

    private final Grid<HopsForInterfaceDto> hopsGrid = new Grid<>(HopsForInterfaceDto.class);

    private Grid<String> foodPairingsGrid=new Grid<>();
    Binder<BeerDto> binder = new BeanValidationBinder<>(BeerDto.class);

    BackEndDataManipulatorService backEndDataManipulatorService;
    boolean isAdmin;
    public BeerViewForm(BackEndDataManipulatorService backEndDataManipulatorService,MainView parent,boolean isAdmin) {
        this.isAdmin=isAdmin;
        this.parentView=parent;
        this.backEndDataManipulatorService=backEndDataManipulatorService;
        yeast.setValue("placeholder");
        brewers_tips.setMaxLength(2000);
        binder.bindInstanceFields(this);
        imageUrl = graphicAssets.imageConfig("ph", embeddedHeight, embeddedWidth);
        textFieldLock(true);
        add(imageUrl,name, description, abv, ibu, target_og, ebc, srm, attenuationLevel, volume, boilVolume,maltName, getMaltsGrid(),
                hopsName, getHopsGrid(),getFoodPairingsGrid(),yeast,contributed_by, createButtonsLayout());
    }
    private void setButtonForRemovingRecipeFromDb(){
        deleteButton.setText("delete recipe from Db");
    }

    public void setButtonForAddingToFavorites(){
        favoritesButton.setText("add to favorites");
        favoritesButton.addClickListener(e-> {
            backEndDataManipulatorService.addBeerToFavorites(beerDto.getName());
        });
    }

    public void setButtonForRemovingFromFavorites(){
        favoritesButton.setText("remove from favorites");
        favoritesButton.addClickListener(e->{
            backEndDataManipulatorService.removeBeerFromFavorites(beerDto.getName());
            parentView.setBeerDtoGridValues(parentView.updateCurrentFavoriteBeerList());
        });

    }


    private void textFieldLock(boolean lockValue) {
        name.setReadOnly(lockValue);
        description.setReadOnly(lockValue);
        abv.setReadOnly(lockValue);
        ibu.setReadOnly(lockValue);
        target_og.setReadOnly(lockValue);
        ebc.setReadOnly(lockValue);
        srm.setReadOnly(lockValue);
        attenuationLevel.setReadOnly(lockValue);
        brewers_tips.setReadOnly(lockValue);
        contributed_by.setReadOnly(lockValue);
        yeast.setReadOnly(lockValue);
    }

    public Grid<String> getFoodPairingsGrid() {
        return foodPairingsGrid;
    }

    public void setFoodPairingsGrid() {
        foodPairingsGrid.removeAllColumns();
        foodPairingsGrid.setItems(beerDto.getFoodPairing());
        foodPairingsGrid.addColumn(e->e).setHeader("Food Pairings");
    }

    public Grid<MaltsForInterfaceDto> getMaltsGrid() {
        return maltsGrid;
    }

    public Grid<HopsForInterfaceDto> getHopsGrid() {
        return hopsGrid;
    }

    public void setMaltsGrid(){
        maltsGrid.setItems(beerDto.getIngredientsDto().getMaltDtoList().stream()
                .map(tempMalts -> new MaltsForInterfaceDto(tempMalts.getName(), tempMalts.getAmountDto().getValue(),
                        tempMalts.getAmountDto().getUnit()))
                .collect(Collectors.toList()));
    }

    public void setHopsGrid(){
        hopsGrid.setItems(beerDto.getIngredientsDto().getHopsDtoList().stream()
                .map(tempHops -> new HopsForInterfaceDto(tempHops.getName(), tempHops.getAmountDto().getValue(),
                        tempHops.getAmountDto().getUnit(), tempHops.getAdd(), tempHops.getAttribute()))
                .collect(Collectors.toList()));
    }

    public void volumeAccordionSetVolume() {
        volumeAccordionClearVolume();
        Span volumeData = new Span("" + beerDto.getVolumeDto().getValue() + " " + beerDto.getVolumeDto().getUnit());
        volumeHorizontalLayout.add(volumeData);
        volumeHorizontalLayout.setSpacing(false);
        volumeHorizontalLayout.setPadding(false);
        volume.add("volume information", volumeHorizontalLayout);
    }

    public void volumeAccordionClearVolume() {
        if (volume.getChildren().findAny().isPresent()) {
            volume.remove(volumeHorizontalLayout);
            volumeHorizontalLayout.removeAll();
        }
    }

    public void boilVolumeAccordionSetVolume() {
        boilVolumeAccordionClearVolume();
        Span volumeData = new Span("" + beerDto.getBoilVolumeDto().getValue() + " " + beerDto.getBoilVolumeDto().getUnit());
        boilVolumeHorizontalLayout.add(volumeData);
        boilVolumeHorizontalLayout.setSpacing(false);
        boilVolumeHorizontalLayout.setPadding(false);
        boilVolume.add("boil volume information", boilVolumeHorizontalLayout);
    }

    public void boilVolumeAccordionClearVolume() {
        if (boilVolume.getChildren().findAny().isPresent()) {
            boilVolume.remove(boilVolumeHorizontalLayout);
            boilVolumeHorizontalLayout.removeAll();
        }
    }

    private HorizontalLayout createButtonsLayout() {
        //default behavior
        setButtonForAddingToFavorites();
        favoritesButton.addClickShortcut(Key.ENTER);
        closeButton.addClickListener(e -> setVisible(false));
        closeButton.addClickShortcut(Key.ESCAPE);
        if (isAdmin){
            setButtonForRemovingRecipeFromDb();
            return new HorizontalLayout(favoritesButton, closeButton,deleteButton);
        }
        return new HorizontalLayout(favoritesButton, closeButton);
    }

    public void setBeer(BeerDto beer) {
        this.beerDto = beer;
        if (beer != null) {
            yeast.setValue(beerDto.getIngredientsDto().getYeast());
            if (beer.getImageUrl() != null) {
                imageUrl = graphicAssets.imageConfig(beer.getImageUrl(), embeddedWidth, embeddedHeight);
            }
        }
        binder.readBean(beer);
    }
}
    // Events
//    public static abstract class MealFormEvent extends ComponentEvent<BeerViewForm> {
//        private BeerDto beerDto;
//
//        protected MealFormEvent(BeerViewForm source, BeerDto contact) {
//            super(source, false);
//            this.beerDto = contact;
//        }
//
//        public BeerDto getBeerDto() {
//            return beerDto;
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
