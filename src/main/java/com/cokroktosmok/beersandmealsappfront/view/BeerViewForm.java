package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.data.dto.beer.BeerDto;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;


public class BeerViewForm extends FormLayout {
    private BeerDto beerDto;
    TextField name = new TextField("name");
    TextField description = new TextField("description");
    TextField image_url = new TextField("image_url");
    TextField abv = new TextField("abv");
    TextField ibu = new TextField("ibu");
    TextField target_og = new TextField("target_og");
    TextField ebc = new TextField("ebc");
    TextField srm = new TextField("srm");
    TextField attenuationLevel = new TextField("attenuationLevel");

    Accordion volume=new Accordion();

    Accordion boilVolume=new Accordion();

    TextArea brewers_tips = new TextArea("brewers_tips");
    TextField contributed_by = new TextField("contributed_by");
    Button favoritesButton = new Button("Favorites");
    Button closeButton = new Button("close");

    HorizontalLayout volumeHorizontalLayout =new HorizontalLayout();

    HorizontalLayout boilVolumeHorizontalLayout =new HorizontalLayout();
    Binder<BeerDto> binder = new BeanValidationBinder<>(BeerDto.class);
    public BeerViewForm() {
        brewers_tips.setMaxLength(2000);
        binder.bindInstanceFields(this);
        add(name,description,image_url, abv,ibu,target_og,ebc,srm,attenuationLevel,volume,boilVolume,brewers_tips,contributed_by,createButtonsLayout());
    }
    public void volumeAccordionSetVolume(){
            volumeAccordionClearVolume();
            Span volumeData=new Span(""+beerDto.getVolumeDto().getValue()+" "+beerDto.getVolumeDto().getUnit());
            volumeHorizontalLayout.add(volumeData);
            volumeHorizontalLayout.setSpacing(false);
            volumeHorizontalLayout.setPadding(false);
            volume.add("volume information", volumeHorizontalLayout);
    }
    public void volumeAccordionClearVolume(){
        if (volume.getChildren().findAny().isPresent()){
            volume.remove(volumeHorizontalLayout);
            volumeHorizontalLayout.removeAll();
        }
    }

    public void boilVolumeAccordionSetVolume(){
        boilVolumeAccordionClearVolume();
        Span volumeData=new Span(""+beerDto.getBoilVolumeDto().getValue()+" "+beerDto.getBoilVolumeDto().getUnit());
        boilVolumeHorizontalLayout.add(volumeData);
        boilVolumeHorizontalLayout.setSpacing(false);
        boilVolumeHorizontalLayout.setPadding(false);
        boilVolume.add("boil volume information", boilVolumeHorizontalLayout);
    }
    public void boilVolumeAccordionClearVolume(){
        if (boilVolume.getChildren().findAny().isPresent()){
            boilVolume.remove(boilVolumeHorizontalLayout);
            boilVolumeHorizontalLayout.removeAll();
        }
    }


//    public Accordion getBoilVolumeAccordionConfigurator(){
//
//    }
//    public Accordion accordionConfigurator(){
//
//    }
    private HorizontalLayout createButtonsLayout() {
        favoritesButton.addClickShortcut(Key.ENTER);
        closeButton.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(favoritesButton, closeButton);
    }

    public void setBeer(BeerDto beer) {
        this.beerDto = beer;
        binder.readBean(beer);
    }
    // Events
    public static abstract class MealFormEvent extends ComponentEvent<BeerViewForm> {
        private BeerDto beerDto;

        protected MealFormEvent(BeerViewForm source, BeerDto contact) {
            super(source, false);
            this.beerDto = contact;
        }

        public BeerDto getBeerDto() {
            return beerDto;
        }
    }

//
//    public static class CloseEvent extends MealFormEvent {
//        CloseEvent(MealViewForm source) {
//            super(source, null);
//        }
//    }
//
//    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
//                                                                  ComponentEventListener<T> listener) {
//        return getEventBus().addListener(eventType, listener);
//    }
}
