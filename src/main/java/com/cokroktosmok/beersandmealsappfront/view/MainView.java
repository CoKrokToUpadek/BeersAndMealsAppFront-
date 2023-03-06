package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.data.dto.beer.BeerDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.MealDto;

import com.cokroktosmok.beersandmealsappfront.data.dto.user.UserDto;
import com.cokroktosmok.beersandmealsappfront.service.BackEndDataManipulatorService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;


import javax.annotation.security.PermitAll;
import java.util.List;

@Route(value = "/")
@PageTitle("BeersAndMeal")
@PermitAll
@CssImport("./styles/styles.css")
public class MainView extends VerticalLayout {

    private final Grid<MealDto> mealDtoGrid = new Grid<>(MealDto.class);
    private final Grid<BeerDto> beerDtoGrid=new Grid<>(BeerDto.class);

    private final Grid<UserDto> usersDtoGrid=new Grid<>(UserDto.class);
    TextField filterBeerText = new TextField();
    TextField filterMealText = new TextField();

    TextField filterUsersText=new TextField();
    MealViewForm mealViewForm;
    BeerViewForm beerViewForm;

    UserViewForm userViewForm;
    BackEndDataManipulatorService backEndDataManipulatorService;

    HorizontalLayout currentlyDisplayedLayout;

   private boolean isAdmin;

   DialogWindow dialogForButtonActions;

    @Autowired
    public MainView(BackEndDataManipulatorService backEndDataManipulatorService) {
        //If I try to get SecurityContextHolder without It being wrapped in function, the content is null (have no idea why)
        setAdmin();
        this.backEndDataManipulatorService=backEndDataManipulatorService;
        this.mealViewForm = new MealViewForm(this.backEndDataManipulatorService,this,isAdmin);
        this.beerViewForm=new BeerViewForm(this.backEndDataManipulatorService,this,isAdmin);
        //this functionality can be accessed only when some 1 is admin so there is no point in checking it here again
        this.userViewForm=new UserViewForm(this.backEndDataManipulatorService,this);
        add(buttons());
        //default value
        this.currentlyDisplayedLayout=beersAndMealsLayoutBuilder();
        add(currentlyDisplayedLayout);
        configureMealViewForm();
        configureBeerViewForm();
        configureUserViewForm();
        setSizeFull();
        mealViewForm.setVisible(false);
        beerViewForm.setVisible(false);
        userViewForm.setVisible(false);
    }



    private HorizontalLayout getToolbar(TextField textField) {
        textField.setPlaceholder("Filter by name...");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        HorizontalLayout toolbar = new HorizontalLayout(textField);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void setAdmin(){
        isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> "ROLE_ADMIN".equals(grantedAuthority.getAuthority()));
    }

    private HorizontalLayout buttons(){
        Button logout = new Button("Log out",e-> UI.getCurrent().navigate("/login"));
        Button favorites = new Button("favorites lists",e-> setLayoutToFavorites());
        Button defaultList = new Button("beers and meals lists",e-> setLayoutToBeersAndMeals());
        if (isAdmin){
            Button admin = new Button("users panel" ,e->setLayoutToUsers());
            Button updateDb = new Button("update local recipes db" ,e->
            {
                List<String> response= updateRecipesDb();
                dialogForButtonActions=new DialogWindow("updating local recipes db from api",response.get(0),response.get(1));
                dialogForButtonActions.getDialog().open();

            });


            Button clearDb = new Button("remove all recipes from db" ,e->
            {
                List<String> response= clearRecipesDb();
                dialogForButtonActions=new DialogWindow("removing all recipes from local db",response.get(0),response.get(1));
                dialogForButtonActions.getDialog().open();
            });
            return new HorizontalLayout(logout,defaultList,favorites,admin,updateDb,clearDb);
        }
        return new HorizontalLayout(logout,defaultList,favorites);
    }

    private HorizontalLayout beersAndMealsLayoutBuilder(){
        HorizontalLayout grids= new HorizontalLayout();
        grids.add(beerViewForm);
        grids.add(populateBeersLayout(backEndDataManipulatorService));
        grids.add(populateMealsLayout(backEndDataManipulatorService));
        grids.add(mealViewForm);
        grids.setSizeFull();
        return grids;
    }

    private HorizontalLayout usersLayoutBuilder(){
        HorizontalLayout grids = new HorizontalLayout();
        grids.add(populateUsersLayout(backEndDataManipulatorService));
        grids.add(userViewForm);
        grids.setSizeFull();
        return grids;
    }

    private VerticalLayout populateUsersLayout(BackEndDataManipulatorService backEndDataManipulatorService){
        VerticalLayout usersLayout=new VerticalLayout();
        filterMealText.addValueChangeListener(e-> updateUserList());
        usersLayout.add(getToolbar(filterUsersText));
        usersDtoGrid.setColumns("login","firstName","lastName");
        usersDtoGrid.setAllRowsVisible(false);
        usersLayout.add(usersDtoGrid);
        List<UserDto> usersDtoList= backEndDataManipulatorService.findAllUsers(null);
        usersDtoGrid.setItems(usersDtoList);
        usersDtoGrid.asSingleSelect().addValueChangeListener(event -> editUser(event.getValue()));
        return usersLayout;

    }



    private VerticalLayout populateMealsLayout(BackEndDataManipulatorService backEndDataManipulatorService){
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

    private VerticalLayout populateBeersLayout(BackEndDataManipulatorService backEndDataManipulatorService){
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

    private void setLayoutToUsers(){
        currentlyDisplayedLayout.removeAll();
        currentlyDisplayedLayout.add(usersLayoutBuilder());
    }


    private void setLayoutToBeersAndMeals(){
        currentlyDisplayedLayout.removeAll();
        currentlyDisplayedLayout.add(beersAndMealsLayoutBuilder());
        List<BeerDto> beerDtoList=backEndDataManipulatorService.findAllBeers(null);
        List<MealDto> mealDtoList=backEndDataManipulatorService.findAllMeals(null);
        beerDtoGrid.setItems(beerDtoList);
        mealDtoGrid.setItems(mealDtoList);
        beerViewForm.setButtonForAddingToFavorites();
        mealViewForm.setButtonForAddingToFavorites();
    }



    private void setLayoutToFavorites(){
        currentlyDisplayedLayout.removeAll();
        currentlyDisplayedLayout.add(beersAndMealsLayoutBuilder());
        List<BeerDto> beerDtoList=  updateCurrentFavoriteBeerList();
        List<MealDto> mealDtoList= updateCurrentFavoriteMealList();
        beerDtoGrid.setItems(beerDtoList);
        mealDtoGrid.setItems(mealDtoList);
        beerViewForm.setButtonForRemovingFromFavorites();
        mealViewForm.setButtonForRemovingFromFavorites();
    }

   public List<BeerDto> updateCurrentFavoriteBeerList(){
        return backEndDataManipulatorService.findFavoriteBeers(null);
    }

    public List<MealDto> updateCurrentFavoriteMealList(){
        return backEndDataManipulatorService.findFavoriteMeals(null);
    }

    public List<UserDto> updateUsersList(){
        return backEndDataManipulatorService.findAllUsers(null);
    }

    public List<String>  clearRecipesDb(){
      List<String> responseMsgs= backEndDataManipulatorService.clearRecipesDb();
        setLayoutToBeersAndMeals();
        return responseMsgs;
    }

    public List<String> updateRecipesDb(){
        List<String> responseMsgs= backEndDataManipulatorService.updateRecipesDb();
        setLayoutToBeersAndMeals();
        return responseMsgs;
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

    public void editUser(UserDto userDto) {
        if (userDto == null) {
            closeUserEditor();
        } else {
            userViewForm.setUser(userDto);
            userViewForm.setVisible(true);
        }
    }

    private void configureUserViewForm(){

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

    public void updateBeerList() {
        beerDtoGrid.setItems(backEndDataManipulatorService.findAllBeers(filterBeerText.getValue()));
    }


    public void updateMealList() {
        mealDtoGrid.setItems(backEndDataManipulatorService.findAllMeals(filterMealText.getValue()));
    }

    private void updateUserList() {
        usersDtoGrid.setItems(backEndDataManipulatorService.findAllUsers(filterUsersText.getValue()));
    }

    private void closeMealEditor() {
        mealViewForm.clearIngredientsList();
        mealViewForm.setMeal(null);
        mealViewForm.setVisible(false);
    }

   private void closeUserEditor(){
        userViewForm.setUser(null);
        userViewForm.setVisible(false);
   }

    private void closeBeerEditor() {
        beerViewForm.volumeAccordionClearVolume();
        beerViewForm.boilVolumeAccordionClearVolume();
        beerViewForm.setBeer(null);
        beerViewForm.setVisible(false);
    }

    public void setMealDtoGridValues(List<MealDto> list) {
         mealDtoGrid.setItems(list);
    }

    public void setUserDtoGridValues(List<UserDto> list){
        usersDtoGrid.setItems(list);
    }

    public void setBeerDtoGridValues(List<BeerDto> list) {
         beerDtoGrid.setItems(list);
    }
}