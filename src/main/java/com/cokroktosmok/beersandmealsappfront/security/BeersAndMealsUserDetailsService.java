package com.cokroktosmok.beersandmealsappfront.security;


import com.cokroktosmok.beersandmealsappfront.config.BackendCommunicationClient;
import com.cokroktosmok.beersandmealsappfront.config.TokenStorage;
import com.cokroktosmok.beersandmealsappfront.data.dto.user.UserCredentialsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;


import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Optional;

@Service
public class BeersAndMealsUserDetailsService implements UserDetailsService {

    BackendCommunicationClient backendCommunicationClient;

    @Autowired
    public BeersAndMealsUserDetailsService(BackendCommunicationClient backendCommunicationClient, Cookie cookie) {
        this.backendCommunicationClient = backendCommunicationClient;

    }

    @Override
    public AppUserDetails loadUserByUsername(String username) {
        Optional< UserCredentialsDto> user=backendCommunicationClient.getUser(username);
        if (user.isPresent()) {
            TokenStorage.putToken(username,user.get().getToken());
            return new AppUserDetails(user.get());
        } else {
            throw new BadCredentialsException("user not found");
        }
    }

}
