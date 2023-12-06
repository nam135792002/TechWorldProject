package com.techworld.setting;

import com.techworld.Utility;
import com.techworld.cart.CartItemService;
import com.techworld.common.entity.CartItem;
import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Setting;
import com.techworld.customer.CustomerNotFoundException;
import com.techworld.customer.CustomerService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SettingFilter implements Filter{
    @Autowired private SettingService settingService;
    @Autowired private CartItemService cartItemService;
    @Autowired private CustomerService customerService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String url = servletRequest.getRequestURL().toString();
        if(url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".png") || url.endsWith(".jpg")){
            chain.doFilter(request,response);
            return;
        }
        List<Setting> generalSettings = settingService.getGeneralSettings();
        Customer customer = null;
        try {
            customer = getAuthenticatsCustomer(servletRequest);
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
        Customer finalCustomer = customer;
        generalSettings.forEach(setting -> {
            request.setAttribute(setting.getKey(),setting.getValue());
            request.setAttribute("total",cartItemService.countByCustomer(finalCustomer));
        });
        chain.doFilter(request,response);
    }

    private Customer getAuthenticatsCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }
}
