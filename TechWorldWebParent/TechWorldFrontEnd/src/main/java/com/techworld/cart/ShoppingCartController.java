package com.techworld.cart;

import com.techworld.Utility;
import com.techworld.common.entity.CartItem;
import com.techworld.common.entity.Customer;
import com.techworld.customer.CustomerNotFoundException;
import com.techworld.customer.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShoppingCartController {
    @Autowired private CartItemService cartItemService;
    @Autowired private CustomerService customerService;

    @GetMapping("/cart")
    public String viewCart(Model model, HttpServletRequest request) throws CustomerNotFoundException {
        Customer customer = getAuthenticatsCustomer(request);
        List<CartItem> cartItems = cartItemService.listCartItem(customer);
        int estimatedTotal = 0;
        for (CartItem item : cartItems){
            estimatedTotal += item.getSubTotal();
        }

        model.addAttribute("cartItems",cartItems);
        model.addAttribute("estimatedTotal",estimatedTotal);
        return "cart/shopping_cart";
    }

    private Customer getAuthenticatsCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }
}
