package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.data.dto.user.CreatedUserDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route(value = "createaccount")
public class CreateAccountForm extends FormLayout {

    private CreatedUserDto createdUserDto;

    TextField firstName = new TextField("firstName");

    TextField lastName = new TextField("lastName");

    TextField address = new TextField("address");

    TextField email = new TextField("email");

    TextField login = new TextField("login");

    TextField password = new TextField("password");


    Button confirm=new Button("confirm");

    Button goBack=new Button("Go back to login page");

    public CreateAccountForm() {
        goBack.addClickListener(event-> UI.getCurrent().navigate("/login"));
        add(firstName,lastName,address,email,login,password,createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        return new HorizontalLayout(confirm, goBack);
    }

}
