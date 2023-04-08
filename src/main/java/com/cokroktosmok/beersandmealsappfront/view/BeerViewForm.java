package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.data.dto.beer.*;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.IngredientAndMeasureDto;
import com.cokroktosmok.beersandmealsappfront.service.BackEndDataManipulatorService;
import com.cokroktosmok.beersandmealsappfront.static_recources.GraphicAssets;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BeerViewForm extends FormLayout {

    private MainView parentView;
    private final int embeddedHeight = 400;
    private final int embeddedWidth = 100;
    private final GraphicAssets graphicAssets = new GraphicAssets();
    private BeerDto beerDto;
    private final TextField name = new TextField("name");
    private final TextArea description = new TextArea("description");
    private  Image imageUrl;
    private final TextField abv = new TextField("abv");
    private final TextField ibu = new TextField("ibu");
    private final TextField target_og = new TextField("target_og");
    private final TextField ebc = new TextField("ebc");
    private final TextField srm = new TextField("srm");
    private final TextField attenuationLevel = new TextField("attenuationLevel");

    private final Accordion volume = new Accordion();

    private final Accordion boilVolume = new Accordion();

    private final TextArea brewers_tips = new TextArea("brewers_tips");
    private final TextField contributed_by = new TextField("contributed_by");
    private final Button favoritesButton = new Button("favorites");//placeholder
    private final Button closeButton = new Button("close");

    private  Span maltName = new Span("Malts list");

    private Span hopsName = new Span("Hops list");

    private final TextField yeast = new TextField("Yeast");

    private final Button deleteButton = new Button();

    private final HorizontalLayout volumeHorizontalLayout = new HorizontalLayout();

    private final HorizontalLayout boilVolumeHorizontalLayout = new HorizontalLayout();

    private final Grid<MaltsForInterfaceDto> maltsGrid = new Grid<>(MaltsForInterfaceDto.class);

    private final Grid<HopsForInterfaceDto> hopsGrid = new Grid<>(HopsForInterfaceDto.class);

    private final Grid<String> foodPairingsGrid = new Grid<>();
    private final Binder<BeerDto> binder = new BeanValidationBinder<>(BeerDto.class);

    private final BackEndDataManipulatorService backEndDataManipulatorService;

    private DialogWindow dialogForButtonActions;
    private final boolean isAdmin;

    //this convoluted way to manipulate eventClickListeners, but it works only this way
    Registration registration;

    public BeerViewForm(BackEndDataManipulatorService backEndDataManipulatorService, MainView parent, boolean isAdmin) {
        this.isAdmin = isAdmin;
        this.parentView = parent;
        this.backEndDataManipulatorService = backEndDataManipulatorService;
        yeast.setValue("placeholder");
        brewers_tips.setMaxLength(2000);
        binder.bindInstanceFields(this);
        imageUrl = graphicAssets.imageConfig("ph", embeddedWidth, embeddedHeight);
        HorizontalLayout imageContainer=new HorizontalLayout();
        imageContainer.add(imageUrl);
        imageContainer.addClassName("image-formatter");
        imageContainer.addClassName("padding-60");
        textFieldLock(true);
        hopsName.addClassName("textfield-config");
        maltName.addClassName("textfield-config");
        add(imageContainer, name, description, abv, ibu, target_og, ebc, srm, attenuationLevel, volume, boilVolume, maltName, getMaltsGrid(),
                hopsName, getHopsGrid(), getFoodPairingsGrid(), yeast, contributed_by, createButtonsLayout());
    }

    private void setButtonForRemovingRecipeFromDb() {
        deleteButton.setText("delete recipe from Db");
        deleteButton.addClickListener(e -> {
            String msg = backEndDataManipulatorService.deleteSingleBeerFromDb(beerDto.getName());
            parentView.updateBeerList();
            dialogForButtonActions = new DialogWindow("Removing recipe from db", msg);
            dialogForButtonActions.getDialog().open();
        });
    }

    public void setButtonForAddingToFavorites() {
        if (registration != null) {
            registration.remove();
        }
        favoritesButton.setText("add to favorites");
        registration = favoritesButton.addClickListener(e -> eventListenerForAddingToFavorites());
    }

    public void setButtonForRemovingFromFavorites() {
        if (registration != null) {
            registration.remove();
        }
        favoritesButton.setText("remove from favorites");
        registration = favoritesButton.addClickListener(e -> eventListenerForRemovingFromFavorites());
    }

    private void eventListenerForAddingToFavorites(){
        String msg = backEndDataManipulatorService.addBeerToFavorites(beerDto.getName());
        dialogForButtonActions = new DialogWindow("adding favorites", msg);
        dialogForButtonActions.getDialog().open();
    }

    private void eventListenerForRemovingFromFavorites(){
        String msg = backEndDataManipulatorService.removeBeerFromFavorites(beerDto.getName());
        parentView.setBeerDtoGridValues(parentView.updateCurrentFavoriteBeerList());
        dialogForButtonActions = new DialogWindow("removing from favorites", msg);
        dialogForButtonActions.getDialog().open();
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

    private Grid<String> getFoodPairingsGrid() {
        return foodPairingsGrid;
    }

    public void setFoodPairingsGrid() {
        foodPairingsGrid.removeAllColumns();
        foodPairingsGrid.setItems(beerDto.getFoodPairing());
        foodPairingsGrid.addColumn(e -> e).setHeader("Food Pairings");
    }

    private Grid<MaltsForInterfaceDto> getMaltsGrid() {
        return maltsGrid;
    }

    private Grid<HopsForInterfaceDto> getHopsGrid() {
        return hopsGrid;
    }

    public void setBeerViewContent(BeerDto beerDto){
        setBeer(beerDto);
        volumeAccordionSetVolume();
        boilVolumeAccordionSetVolume();
        setHopsGrid();
        setMaltsGrid();
        setFoodPairingsGrid();
        setVisible(true);
    }

    public void clearBeerViewContent(){
        volumeAccordionClearVolume();
        boilVolumeAccordionClearVolume();
        setBeer(null);
        setVisible(false);
    }


    private void setMaltsGrid() {
        maltsGrid.setItems(beerDto.getIngredientsDto().getMaltDtoList().stream()
                .map(tempMalts -> new MaltsForInterfaceDto(tempMalts.getName(), tempMalts.getAmountDto().getValue(),
                        tempMalts.getAmountDto().getUnit()))
                .collect(Collectors.toList()));
    }

    private void setHopsGrid() {
        hopsGrid.setItems(beerDto.getIngredientsDto().getHopsDtoList().stream()
                .map(tempHops -> new HopsForInterfaceDto(tempHops.getName(), tempHops.getAmountDto().getValue(),
                        tempHops.getAmountDto().getUnit(), tempHops.getAdd(), tempHops.getAttribute()))
                .collect(Collectors.toList()));
    }

    private void volumeAccordionSetVolume() {
        volumeAccordionClearVolume();
        Span volumeData = new Span("" + beerDto.getVolumeDto().getValue() + " " + beerDto.getVolumeDto().getUnit());
        volumeData.addClassName("textfield-config");
        volumeHorizontalLayout.add(volumeData);
        volumeHorizontalLayout.setSpacing(false);
        volumeHorizontalLayout.setPadding(false);
        volume.add("volume information", volumeHorizontalLayout);
    }

    private void volumeAccordionClearVolume() {
        if (volume.getChildren().findAny().isPresent()) {
            volume.remove(volumeHorizontalLayout);
            volumeHorizontalLayout.removeAll();
        }
    }

    private void boilVolumeAccordionSetVolume() {
        boilVolumeAccordionClearVolume();
        Span volumeData = new Span("" + beerDto.getBoilVolumeDto().getValue() + " " + beerDto.getBoilVolumeDto().getUnit());
        volumeData.addClassName("textfield-config");
        boilVolumeHorizontalLayout.add(volumeData);
        boilVolumeHorizontalLayout.setSpacing(false);
        boilVolumeHorizontalLayout.setPadding(false);
        boilVolume.add("boil volume information", boilVolumeHorizontalLayout);
    }

    private void boilVolumeAccordionClearVolume() {
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
        if (isAdmin) {
            setButtonForRemovingRecipeFromDb();
            return new HorizontalLayout(favoritesButton, closeButton, deleteButton);
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
