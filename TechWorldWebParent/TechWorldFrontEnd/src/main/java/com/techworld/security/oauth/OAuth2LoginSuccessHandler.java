package com.techworld.security.oauth;

import com.techworld.common.entity.AuthenticationType;
import com.techworld.common.entity.Customer;
import com.techworld.customer.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final CustomerService customerService;

    @Autowired
    public OAuth2LoginSuccessHandler(@Lazy CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomerOauth2User oauth2User = (CustomerOauth2User) authentication.getPrincipal();
        String name = oauth2User.getName();
        String email = oauth2User.getEmail();
        String clientName = oauth2User.getClientName();
        AuthenticationType authenticationType = getAuthenticationType(clientName);
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null){
            customerService.addNewCustomerUponOAuthLogin(name, email, authenticationType);
        }else{
            oauth2User.setFullName(customer.getFullName());
            customerService.updateAuthenticationType(customer, authenticationType);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private AuthenticationType getAuthenticationType(String clientName){
        if(clientName.equals("Google")){
            return AuthenticationType.GOOGLE;
        } else if (clientName.equals("Facebook")) {
            return AuthenticationType.FACEBOOK;
        } else {
            return AuthenticationType.DATABASE;
        }
    }
}
