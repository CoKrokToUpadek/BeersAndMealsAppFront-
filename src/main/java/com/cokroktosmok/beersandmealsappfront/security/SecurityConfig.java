package com.cokroktosmok.beersandmealsappfront.security;

import com.cokroktosmok.beersandmealsappfront.view.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

  private final BeersAndMealsUserDetailsService beerUserDetailsService;

  @Autowired
  public SecurityConfig(BeersAndMealsUserDetailsService beerUserDetailsService) {
    this.beerUserDetailsService = beerUserDetailsService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.userDetailsService(beerUserDetailsService);
    setLoginView(http, LoginView.class);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    super.configure(web);
  }


}