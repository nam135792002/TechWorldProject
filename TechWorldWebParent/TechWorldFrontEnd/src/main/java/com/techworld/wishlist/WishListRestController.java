package com.techworld.wishlist;

import com.techworld.Utility;
import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Product;
import com.techworld.common.entity.Wishlist;
import com.techworld.customer.CustomerNotFoundException;
import com.techworld.customer.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WishListRestController {

    @Autowired private WishListService wishListService;
    @Autowired private CustomerService customerService;

    private Customer getAuthenticatsCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if(email == null){
            throw new CustomerNotFoundException("No authenticated customer");
        }
        return customerService.getCustomerByEmail(email);
    }

    @PostMapping("/wishlist/add/{productId}")
    public String addProductToWishList(@PathVariable(name = "productId") Integer productId, HttpServletRequest request){
        try{
            Customer customer = getAuthenticatsCustomer(request);
            Wishlist wishlist = wishListService.addProductIntoWishList(productId, customer);
            if(wishlist != null){
                long totalWishList = wishListService.countWishListByCustomer(customer);
                return "Đã thêm vào danh sách yêu thích thành công!" + "|" + totalWishList;
            }else{
                return "Bạn đã thêm sản phẩm này vào trước đó!";
            }
        }catch (CustomerNotFoundException e){
            return "Bạn cần phải đăng nhập.";
        }
    }

    @DeleteMapping("/wishlist/remove/{productId}")
    public String removeProductIntoWishList(@PathVariable(name = "productId") Integer productId, HttpServletRequest request){
        try{
            Customer customer = getAuthenticatsCustomer(request);
            wishListService.deleteProductWishList(productId, customer.getId());
            long totalWishList = wishListService.countWishListByCustomer(customer);
            return "Sản phẩm đã được xóa khỏi danh sách thành công." + "|" + totalWishList;
        }catch (CustomerNotFoundException e){
            return "Bạn cần phải đăng nhập.";
        }
    }
}
