package com.techworld.cart;

import com.techworld.Utility;
import com.techworld.common.entity.Customer;
import com.techworld.customer.CustomerNotFoundException;
import com.techworld.customer.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingCartRestController {
    @Autowired private CartItemService cartItemService;
    @Autowired private CustomerService customerService;

    @PostMapping("/cart/add/{productId}/{quantity}")
    public String addProductToCart(@PathVariable(name = "productId") Integer productId,
                                   @PathVariable("quantity") int quantity,
                                   HttpServletRequest request){
        try {
            Customer customer = getAuthenticatsCustomer(request);
            Integer updatedQuantity = cartItemService.addProduct(productId, quantity, customer);
            long total = cartItemService.countByCustomer(customer);

            return "Sản phẩm được thêm vào giỏ thành công." + "|" + total;
        } catch (CustomerNotFoundException e) {
            return "Bạn cần phải đăng nhập để thêm sản phẩm vào giỏ hàng.";
        } catch (ShoppingCartException e) {
            return e.getMessage();
        }
    }

    private Customer getAuthenticatsCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if(email == null){
            throw new CustomerNotFoundException("No authenticated customer");
        }
        return customerService.getCustomerByEmail(email);
    }

    @PostMapping("/cart/update/{productId}/{quantity}")
    public String updateQuantity(@PathVariable(name = "productId") Integer productId,
                                   @PathVariable("quantity") int quantity,
                                   HttpServletRequest request) {
        try {
            Customer customer = getAuthenticatsCustomer(request);
            int subTotal = cartItemService.updatedQuantity(productId, quantity, customer);
            return String.valueOf(subTotal);
        } catch (CustomerNotFoundException e) {
            return "Bạn cần phải đăng nhập để thêm sản phẩm vào giỏ hàng.";
        }
    }

    @DeleteMapping("/cart/remove/{productId}")
    public String removeProduct(@PathVariable("productId") Integer productId, HttpServletRequest request){
        try {
            Customer customer = getAuthenticatsCustomer(request);
            cartItemService.removeProduct(productId, customer);
            long total = cartItemService.countByCustomer(customer);
            return "Sản phẩm đã được xóa khỏi giỏ hàng thành công." + "|" + total;
        } catch (CustomerNotFoundException e) {
            return "Bạn cần phải đăng nhập để thêm sản phẩm vào giỏ hàng.";
        }

    }
}
