package com.cokroktosmok.beersandmealsappfront.view;


import com.cokroktosmok.beersandmealsappfront.data.dto.user.UserDto;
import com.cokroktosmok.beersandmealsappfront.service.BackEndDataManipulatorService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class UserViewForm extends FormLayout {

    public enum statusEnum{
        inactive,active
    }

    MainView parentView;
    private UserDto userDto;
    TextField id = new TextField("ID");
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField address = new TextField("Address");
    TextField email = new TextField("Email");
    TextField login = new TextField("Login");
    TextField userRole = new TextField("User Role");
    DatePicker creationDate = new DatePicker("Creation Date");
    TextField status = new TextField("Status (1 for active,0 for inactive)");

    ComboBox<String> rolesComboBox = new ComboBox<>("change user roles");
    ComboBox<statusEnum> statusComboBox = new ComboBox<>("change user status");

    List<String> rolesList=List.of("user","admin");
    Button applyChanges = new Button("apply changes");
    Button closeButton = new Button("close");

    Binder<UserDto> binder = new BeanValidationBinder<>(UserDto.class);
    BackEndDataManipulatorService backEndDataManipulatorService;

    DialogWindow dialogForButtonActions;

    public UserViewForm(BackEndDataManipulatorService backEndDataManipulatorService,MainView parentView) {
        this.parentView = parentView;
        this.backEndDataManipulatorService = backEndDataManipulatorService;
        binder.bindInstanceFields(this);
        textFieldLock(true);
        populateComboBoxes();
        this.addClassName("padding-60");
        add(id,firstName,lastName,address,email,login,creationDate,userRole,status,comboBoxesLayout(),buttonsLayout());
    }

    private void populateComboBoxes(){
        rolesComboBox.setItems(rolesList);
        rolesComboBox.setValue(rolesList.get(0));
        statusComboBox.setItems(statusEnum.values());
        statusComboBox.setValue(statusEnum.active);
    }

    private HorizontalLayout comboBoxesLayout(){
        HorizontalLayout layout=new HorizontalLayout();
        layout.add(rolesComboBox);
        layout.add(statusComboBox);
        return layout;
    }

    private HorizontalLayout buttonsLayout(){
        HorizontalLayout layout=new HorizontalLayout();
        closeButton.addClickListener(e -> setVisible(false));
        closeButton.addClickShortcut(Key.ESCAPE);
        applyChanges.addClickListener(e->
                {
                    List <String>msgList=modifyUserValues();
                    dialogForButtonActions=new DialogWindow("status of user modification",msgList.get(0), msgList.get(1));
                    dialogForButtonActions.getDialog().open();
                });
        applyChanges.addClickShortcut(Key.ENTER);
        layout.add(applyChanges);
        layout.add(closeButton);
        return layout;
    }

    private List<String> modifyUserValues() {
         String roleMsg = backEndDataManipulatorService.setUserRole(login.getValue(),rolesComboBox.getValue());
         String statusMsg= backEndDataManipulatorService.setUserStatus(login.getValue(),statusComboBox.getValue().ordinal());
         parentView.setUserDtoGridValues(parentView.updateUsersList());
         return List.of(roleMsg,statusMsg);
    }

    private void textFieldLock(boolean lockValue) {
        id.setReadOnly(lockValue);
        firstName.setReadOnly(lockValue);
        lastName.setReadOnly(lockValue);
        address.setReadOnly(lockValue);
        email.setReadOnly(lockValue);
        login.setReadOnly(lockValue);
        userRole.setReadOnly(lockValue);
        creationDate.setReadOnly(lockValue);
        status.setReadOnly(lockValue);
        statusComboBox.isReadOnly();
        rolesComboBox.isReadOnly();
    }

    public void setUserViewContent(UserDto userDto){
        setUser(userDto);
        setVisible(true);
    }

    public void clearUserViewContent(){
       setUser(null);
        setVisible(false);
    }


    private void setUser(UserDto user) {
        this.userDto = user;
        binder.readBean(user);
    }

}
