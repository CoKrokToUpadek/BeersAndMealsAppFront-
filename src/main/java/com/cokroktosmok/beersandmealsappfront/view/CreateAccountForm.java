package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.static_recources.GraphicAssets;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;


import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = "create_account")
@AnonymousAllowed
public class CreateAccountForm extends VerticalLayout {

    TextField firstName = new TextField("Firs Name");

    TextField lastName = new TextField("Last Name");

    TextField address = new TextField("address");

    TextField email = new TextField("email");//EmailField does not have setRequired method, so i made a textField here

    TextField login = new TextField("login");

    PasswordField password = new PasswordField("password");

    PasswordField confirmPassword = new PasswordField("confirm password");

    Button confirm = new Button("confirm");

    Button goBack = new Button("Go back to login page");

    String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$";

    String loginPattern = "^\\S{4,}$";

    String emailPattern="^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";


    public CreateAccountForm() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        add(GraphicAssets.logoConfig(400, 400));
        address.setWidth("400px");
        add(emailConfigAndLayout(email));
        add(loginConfigAndLayout(login));
        add(firstNameLastNameLayout(firstName, lastName));
        add(address, email);
        add(passwordFieldConfigAndLayout(password, confirmPassword));
        add(createButtonsConfigurationAndLayout(confirm, goBack));

    }

    private void checkForValidFields() {
        boolean fieldValidator = (!firstName.isInvalid() && !lastName.isInvalid() && !email.isInvalid() && !email.isEmpty() &&
                !login.isInvalid() && !password.isInvalid() && !password.isEmpty() && !confirmPassword.isInvalid());
        confirm.setEnabled(fieldValidator);
    }

    private HorizontalLayout createButtonsConfigurationAndLayout(Button confirm, Button goBack) {
        goBack.addClickListener(event -> UI.getCurrent().navigate("/login"));
        confirm.setEnabled(false);
        firstName.addValueChangeListener(e -> checkForValidFields());
        lastName.addValueChangeListener(e -> checkForValidFields());
        email.addValueChangeListener(e->checkForValidFields());
        address.addValueChangeListener(e -> checkForValidFields());
        login.addValueChangeListener(e -> checkForValidFields());
        password.addValueChangeListener(e -> checkForValidFields());
        confirmPassword.addValueChangeListener(e -> checkForValidFields());
        return new HorizontalLayout(confirm, goBack);
    }


    private HorizontalLayout loginConfigAndLayout(TextField login) {
        login.setRequired(true);
        login.setHelperText("login must contain at least 4 characters and cannot contain spaces");
        login.setPattern(loginPattern);
        login.setErrorMessage("login does not match criteria");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        login.setWidth("400px");
        horizontalLayout.add(login);
        return horizontalLayout;

    }

    private HorizontalLayout emailConfigAndLayout(TextField email) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        email.setRequired(true);
        email.setPattern(emailPattern);
        email.setErrorMessage("Email does not match pattern");
        email.setWidth("400px");
        email.setHelperText("Note-email is not validated in this app and there are no functionalities to it other than saving this field value in db.");
        horizontalLayout.add(email);
        return horizontalLayout;
    }

    private HorizontalLayout firstNameLastNameLayout(TextField firstName, TextField lastName) {
        return new HorizontalLayout(firstName, lastName);
    }

    private HorizontalLayout passwordFieldConfigAndLayout(PasswordField password, PasswordField confirmPassword) {
        password.setRequired(true);
        password.setHelperText("A password must be at least 8 characters. It has to have at least one letter and one digit.");
        password.setPattern(passwordPattern);
        password.setErrorMessage("Not a valid password");
        confirmPassword.setRequired(true);
        confirmPassword.setHelperText("Passwords must match");


        password.addValueChangeListener(e -> {
            confirmPassword.setInvalid(!password.getValue().equals(confirmPassword.getValue()));
        });

        confirmPassword.addValueChangeListener(e -> {
            if (!confirmPassword.getValue().equals(password.getValue())) {
                confirmPassword.setInvalid(true);
                confirmPassword.setErrorMessage("Passwords do not match");
            } else {
                confirmPassword.setInvalid(false);
            }
        });
        return new HorizontalLayout(password, confirmPassword);
    }
}
