package com.cokroktosmok.beersandmealsappfront.errorhandlers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;


import java.io.IOException;
import java.util.List;

public class TeaPotCodeInterceptor  implements ClientHttpRequestInterceptor {
    private List<HttpStatus> statusCodes;

    public TeaPotCodeInterceptor(List<HttpStatus> statusCode) {
        this.statusCodes = statusCode;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
//        if (response.getStatusCode().equals(statusCodes) || response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
//            VaadinSession.getCurrent().getSession().invalidate();
//            UI.getCurrent().navigate("login");
//        }
        statusCodes.forEach(e-> {
            try {
                if (response.getStatusCode().equals(e)){
                    VaadinSession.getCurrent().getSession().invalidate();
                    UI.getCurrent().navigate("login");
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return response;
    }
}
