package com.techworld.cart;

import com.techworld.common.entity.CartItem;
import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Product;
import com.techworld.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartItemService {
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductRepository productRepository;
    public Integer addProduct(Integer productId, Integer quantity, Customer customer) throws ShoppingCartException {
        Integer updatedQuantity = quantity;
        Product product = new Product(productId);

        CartItem cartItem = cartItemRepository.findByCustomerAndProduct(customer,product);

        if(cartItem != null){
            updatedQuantity = cartItem.getQuantity() + quantity;
            if(updatedQuantity > 3){
                throw new ShoppingCartException("Lưu ý: " +
                        "giỏ hàng của bạn đã có " + cartItem.getQuantity() + " sản phẩm. " +
                        "Số lượng tối đa là 3.");
            }
        }else{
            cartItem = new CartItem();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
        }
        cartItem.setQuantity(updatedQuantity);
        cartItemRepository.save(cartItem);
        return updatedQuantity;
    }

    public List<CartItem> listCartItem(Customer customer){
        return cartItemRepository.findByCustomer(customer);
    }

    public int updatedQuantity(Integer productId, Integer quantity, Customer customer){
        cartItemRepository.updateQuantity(quantity,customer.getId(), productId);
        Product product = productRepository.findById(productId).get();
        return product.getNewPrice() * quantity;
    }

    public void removeProduct(Integer productId, Customer customer){
        cartItemRepository.deleteByCustomerAndProduct(customer.getId(), productId);
    }

    public void deleteByCustomer(Customer customer){
        cartItemRepository.deleteByCustomer(customer.getId());
    }

    public long countByCustomer(Customer customer){
        return cartItemRepository.countCartItemByCustomer(customer);
    }
}
