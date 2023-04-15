package com.cokroktosmok.beersandmealsappfront.view;

import com.cokroktosmok.beersandmealsappfront.config.BackendCommunicationClient;
import com.cokroktosmok.beersandmealsappfront.data.dto.user.CreatedUserDto;
import com.cokroktosmok.beersandmealsappfront.errorhandlers.UserCreationException;
import com.cokroktosmok.beersandmealsappfront.static_recources.GraphicAssets;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;


import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Route(value = "create_account")
@AnonymousAllowed
public class CreateAccountForm extends VerticalLayout {
    @PropertyId("firstName")
    TextField firstName = new TextField("First Name");
    @PropertyId("lastName")
    TextField lastName = new TextField("Last Name");
    @PropertyId("address")
    TextField address = new TextField("address");
    @PropertyId("email")
    TextField email = new TextField("email");//EmailField does not have setRequired method, so I made a textField here
    @PropertyId("login")
    TextField login = new TextField("login");
    @PropertyId("password")
    PasswordField password = new PasswordField("password");

    PasswordField confirmPassword = new PasswordField("confirm password");

    Button confirm = new Button("confirm");

    Button goBack = new Button("Go back to login page");

    String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$";

    String loginPattern = "^\\S{4,}$";

    String emailPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    BackendCommunicationClient backendCommunicationClient;
    Binder<CreatedUserDto> binder = new Binder<>(CreatedUserDto.class);
    CreatedUserDto createdUserDto;
    Dialog dialog = new Dialog();
    ResponseEntity<Boolean> booleanResponseEntity;

    ResponseEntity<String> stringResponseEntity;

    GraphicAssets graphicAssets=new GraphicAssets();


    @Autowired
    public CreateAccountForm(BackendCommunicationClient backendCommunicationClient, CreatedUserDto createdUserDto) {
        UI.getCurrent().getPage().executeJs("document.documentElement.setAttribute('theme', '"+ Lumo.DARK+"')");
        this.setClassName("create-account-form");
        this.createdUserDto = createdUserDto;
        binder.setBean(createdUserDto);
        this.backendCommunicationClient = backendCommunicationClient;
        createdUserBinderConfig();
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        add(graphicAssets.logoConfig(400, 400));
        address.setWidth("400px");
        add(emailConfigAndLayout(email));
        add(loginConfigAndLayout(login));
        add(firstNameLastNameLayout(firstName, lastName));
        add(address, email);
        add(passwordFieldConfigAndLayout(password, confirmPassword));
        add(createButtonsConfigurationAndLayout(confirm, goBack));

    }

    private void createdUserBinderConfig() {
        binder.forField(login).withValidator(value -> {
                    //for connection problems
                    booleanResponseEntity = backendCommunicationClient.checkIfLoginIsTaken(value);
                    if (booleanResponseEntity.getStatusCode() == HttpStatus.OK) {
                        return Boolean.FALSE.equals(booleanResponseEntity.getBody());
                    } else {
                        dialogConfig(booleanResponseEntity.getStatusCode().toString());
                        return true;
                    }
                }, "login is already taken")
                .withValidator(value -> value.matches(loginPattern), "login does not match criteria").asRequired("cannot be empty").bind(CreatedUserDto::getLogin, CreatedUserDto::setLogin);
        binder.forField(email).asRequired("cannot be empty").withValidator(value -> value.matches(emailPattern), "password does not match criteria")
                .bind(CreatedUserDto::getEmail, CreatedUserDto::setEmail);
        binder.forField(password).asRequired("cannot be empty").withValidator(value -> value.matches(passwordPattern), "password does not match criteria")
                .bind(CreatedUserDto::getPassword, CreatedUserDto::setPassword);
        binder.forField(confirmPassword).asRequired("passwords do not match").withValidator(value -> passwordMatcher(value, password.getValue()),
                "passwords do not match").bind(CreatedUserDto::getPassword, CreatedUserDto::setPassword);
        binder.bindInstanceFields(this);
    }

    public void dialogConfig(String e) {
        dialog.removeAll();
        dialog.getFooter().removeAll();
        Button button = new Button();
        VerticalLayout verticalLayout = new VerticalLayout();
        if (e.equals(UserCreationException.ERR_EMAIL_TAKEN) || e.equals(UserCreationException.ERR_LOGIN_TAKEN) || e.equals(UserCreationException.ERR_MISSING_INFORMATION)) {
            dialog.setHeaderTitle("Ups! There Was problem");
            dialog.add(new Span(e));
            button.addClickListener(r -> dialog.close());
            button.setText("close");
            verticalLayout.add(button);
            dialog.getFooter().add(verticalLayout);
            dialog.open();
        } else if (e.equals("user was created successfully")) {
            dialog.setHeaderTitle("Success!");
            dialog.add(new Span(e));
            button.addClickListener(r -> {
                goBack.click();
                dialog.close();
            });
            button.setText("return to login page");
            verticalLayout.add(button);
            dialog.getFooter().add(verticalLayout);
            dialog.open();
        } else {
            dialog.setHeaderTitle("Something went bad");
            dialog.add(e);
            button.addClickListener(r -> {
                goBack.click();
                dialog.close();
            });
            button.setText("return to login page");
            verticalLayout.add(button);
            dialog.getFooter().add(verticalLayout);
            dialog.addDialogCloseActionListener(event -> {
                UI.getCurrent().navigate("/login");
                dialog.close();
            });
            dialog.open();
        }

    }

    private HorizontalLayout createButtonsConfigurationAndLayout(Button confirm, Button goBack) {
        confirm.setEnabled(false);
        goBack.addClickListener(event -> UI.getCurrent().navigate("/login"));
        binder.addStatusChangeListener(value -> confirm.setEnabled(binder.isValid()));
        confirm.addClickListener(e -> {
            stringResponseEntity = backendCommunicationClient.createUser(createdUserDto);
            if (stringResponseEntity.getStatusCode() == HttpStatus.OK) {
                dialogConfig(stringResponseEntity.getBody());
            } else {
                dialogConfig(stringResponseEntity.getStatusCode().toString());
            }
        });
        return new HorizontalLayout(confirm, goBack);
    }


    private HorizontalLayout loginConfigAndLayout(TextField login) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        login.setRequired(true);
        login.setWidth("400px");
        login.setHelperText("Login must be at least 4 characters long.");
        horizontalLayout.add(login);
        return horizontalLayout;

    }

    private HorizontalLayout emailConfigAndLayout(TextField email) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        email.setWidth("400px");
        email.setHelperText("Note-email is not validated in this app and there are no functionalities to it other than saving this field value in db.");
        horizontalLayout.add(email);
        return horizontalLayout;
    }

    private HorizontalLayout firstNameLastNameLayout(TextField firstName, TextField lastName) {
        return new HorizontalLayout(firstName, lastName);
    }

    private HorizontalLayout passwordFieldConfigAndLayout(PasswordField password, PasswordField confirmPassword) {
        //field specific binders for smother UI
        Binder.Binding<CreatedUserDto, String> passwordBinder = binder.forField(password).asRequired("cannot be empty").withValidator(value -> value.matches(passwordPattern), "password does not match criteria")
                .bind(CreatedUserDto::getPassword, CreatedUserDto::setPassword);
        Binder.Binding<CreatedUserDto, String> confirmPasswordBinder = binder.forField(confirmPassword).asRequired("passwords do not match").withValidator(value -> passwordMatcher(value, password.getValue()),
                "passwords do not match").bind(CreatedUserDto::getPassword, CreatedUserDto::setPassword);
        password.setValueChangeMode(ValueChangeMode.EAGER);
        confirmPassword.setValueChangeMode(ValueChangeMode.EAGER);
        password.addValueChangeListener(value -> {
            passwordBinder.validate();
            confirmPasswordBinder.validate();
        });
        confirmPassword.addValueChangeListener(value -> {
            passwordBinder.validate();
            confirmPasswordBinder.validate();
        });
        password.setHelperText("A password must be at least 8 characters. It has to have at least one letter and one digit.");
        return new HorizontalLayout(password, confirmPassword);
    }

    private boolean passwordMatcher(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
