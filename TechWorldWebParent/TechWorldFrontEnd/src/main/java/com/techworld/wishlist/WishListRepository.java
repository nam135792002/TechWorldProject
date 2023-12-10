package com.techworld.wishlist;

import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Product;
import com.techworld.common.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishListRepository extends JpaRepository<Wishlist, Integer> {
    public Wishlist findByProductAndCustomer(Product product, Customer customer);

    public List<Wishlist> findByCustomer(Customer customer);

    public Long countByCustomer(Customer customer);

    @Modifying
    @Query("delete from Wishlist w where w.product.id = ?1 and w.customer.id = ?2")
    public void deleteByCustomerAndProduct(Integer productId, Integer customerId);
}
