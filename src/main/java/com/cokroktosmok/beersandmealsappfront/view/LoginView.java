package com.cokroktosmok.beersandmealsappfront.view;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;


@Route("login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

   private final LoginForm loginForm = new LoginForm();

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        loginForm.setI18n(configureForm());
        loginForm.setAction("login");
        loginForm.addForgotPasswordListener(event-> UI.getCurrent().navigate("/createaccount"));
        add(new H1("BeersAndMeals"), loginForm);
    }

    private LoginI18n configureForm(){
        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setForgotPassword("create account");
        i18n.setForm(i18nForm);
        return i18n;
    }
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
                 loginForm.setError(true);
        }
    }
}
