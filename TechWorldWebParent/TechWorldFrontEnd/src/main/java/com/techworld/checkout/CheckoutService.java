package com.techworld.checkout;

import com.techworld.common.entity.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {
    public CheckoutInfo prepareCheckout(List<CartItem> cartItems){
        CheckoutInfo checkoutInfo = new CheckoutInfo();

        int productTotal = calculateProductTotal(cartItems);

        checkoutInfo.setProductTotal(productTotal);
        return checkoutInfo;
    }

    private int calculateProductTotal(List<CartItem> cartItems) {
        int total = 0;

        for(CartItem item : cartItems){
            total += item.getSubTotal();
        }
        return total;
    }
}
