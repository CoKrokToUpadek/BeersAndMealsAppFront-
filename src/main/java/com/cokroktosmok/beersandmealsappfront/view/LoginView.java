package com.cokroktosmok.beersandmealsappfront.view;
import com.cokroktosmok.beersandmealsappfront.static_recources.GraphicAssets;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import javax.annotation.security.DenyAll;


@Route("login")
@DenyAll
@CssImport(value = "./styles/loginFormStyles.css",themeFor = "vaadin-login-form-wrapper")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

   private final LoginForm loginForm = new LoginForm();
   GraphicAssets graphicAssets=new GraphicAssets();

    public LoginView() {
        UI.getCurrent().getPage().executeJs("document.documentElement.setAttribute('theme', '"+Lumo.DARK+"')");
        addClassName("create-account-form");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        add(graphicAssets.logoConfig(400, 400));
        add(titleConfig());
        loginForm.setI18n(configureForm());
        loginForm.setAction("login");
        loginForm.addForgotPasswordListener(event-> UI.getCurrent().getPage().setLocation ("create_account"));
        add(loginForm);
    }

    private LoginI18n configureForm(){
        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("");
        i18nForm.setForgotPassword("create account");
        i18n.setForm(i18nForm);
        return i18n;
    }

    private Span titleConfig(){
        Span span=new Span();
        span.setText("Login");
        span.addClassName("login-config");
        return span;
    }
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
                 loginForm.setError(true);
        }
    }
}
