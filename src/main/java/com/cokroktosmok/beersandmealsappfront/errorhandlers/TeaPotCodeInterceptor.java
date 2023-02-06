package com.cokroktosmok.beersandmealsappfront.errorhandlers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;


import java.io.IOException;

public class TeaPotCodeInterceptor  implements ClientHttpRequestInterceptor {
    private HttpStatus statusCode;
    private String message;

    public TeaPotCodeInterceptor(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        if (response.getStatusCode().equals(statusCode)) {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().navigate("login");
        }
        return response;
    }
}
