package com.cokroktosmok.beersandmealsappfront.view;


import com.cokroktosmok.beersandmealsappfront.data.dto.user.UserDto;
import com.cokroktosmok.beersandmealsappfront.service.BackEndDataManipulatorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class UserViewForm extends FormLayout {

    MainView parentView;
    private UserDto userDto;
    TextField id = new TextField("ID");
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField address = new TextField("Address");
    TextField email = new TextField("Email");
    TextField login = new TextField("Login");
    TextField userRole = new TextField("User Role");
    TextField creationDate = new TextField("Creation Date");
    TextField status = new TextField("Status (1 for active,0 for inactive)");

    ComboBox<String> rolesComboBox = new ComboBox<>("change user roles");
    ComboBox<String> statusComboBox = new ComboBox<>("change user status");

    List<String> rolesList=List.of("user","admin");
    List<String> statusList=List.of("active","inactive");

    Button applyChanges = new Button("apply changes");
    Button closeButton = new Button("close");

    Binder<UserDto> binder = new BeanValidationBinder<>(UserDto.class);
    BackEndDataManipulatorService backEndDataManipulatorService;

    public UserViewForm(MainView parentView, BackEndDataManipulatorService backEndDataManipulatorService) {
        this.parentView = parentView;
        this.backEndDataManipulatorService = backEndDataManipulatorService;
        populateComboBoxes();
        add(id,firstName,lastName,address,email,login,creationDate,userRole,status,comboBoxesLayout(),buttonsLayout());
    }

    private void populateComboBoxes(){
        rolesComboBox.setItems(rolesList);
        statusComboBox.setItems(statusList);
    }

    private HorizontalLayout comboBoxesLayout(){
        HorizontalLayout layout=new HorizontalLayout();
        layout.add(rolesComboBox);
        layout.add(statusComboBox);
        return layout;
    }

    private HorizontalLayout buttonsLayout(){
        HorizontalLayout layout=new HorizontalLayout();
        layout.add(applyChanges);
        layout.add(closeButton);
        return layout;
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
        statusComboBox.setReadOnly(lockValue);
    }

}
