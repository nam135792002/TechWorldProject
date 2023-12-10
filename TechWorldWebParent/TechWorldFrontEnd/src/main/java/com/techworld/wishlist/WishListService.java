package com.techworld.wishlist;

import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Product;
import com.techworld.common.entity.Wishlist;
import com.techworld.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class WishListService {
    @Autowired private WishListRepository wishListRepository;
    @Autowired private ProductRepository productRepository;

    public Wishlist addProductIntoWishList(Integer productId, Customer customer){
        Product product = productRepository.findById(productId).get();

        Wishlist wishlist = wishListRepository.findByProductAndCustomer(product, customer);
        if(wishlist == null){
            wishlist = new Wishlist();
            wishlist.setProduct(product);
            wishlist.setCustomer(customer);
            return wishListRepository.save(wishlist);
        }else{
            return null;
        }
    }

    public List<Wishlist> listWishList(Customer customer){
        return wishListRepository.findByCustomer(customer);
    }
    public void deleteProductWishList(Integer productId, Integer customerId){
        wishListRepository.deleteByCustomerAndProduct(productId, customerId);
    }

    public Long countWishListByCustomer(Customer customer){
        return wishListRepository.countByCustomer(customer);
    }
}
