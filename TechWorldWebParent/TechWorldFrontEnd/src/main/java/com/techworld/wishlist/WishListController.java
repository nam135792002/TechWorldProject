package com.techworld.wishlist;

import com.techworld.Utility;
import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Wishlist;
import com.techworld.customer.CustomerNotFoundException;
import com.techworld.customer.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WishListController {

    @Autowired private WishListService wishListService;
    @Autowired private CustomerService customerService;

    @GetMapping("/wishlist")
    public String listAll(HttpServletRequest request, Model model) throws CustomerNotFoundException {
        Customer customer = getAuthenticatsCustomer(request);
        List<Wishlist> listWishLists = wishListService.listWishList(customer);

        model.addAttribute("listWishLists", listWishLists);
        return "wishlist/wishlist";
    }

    private Customer getAuthenticatsCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if(email == null){
            throw new CustomerNotFoundException("No authenticated customer");
        }
        return customerService.getCustomerByEmail(email);
    }
}
