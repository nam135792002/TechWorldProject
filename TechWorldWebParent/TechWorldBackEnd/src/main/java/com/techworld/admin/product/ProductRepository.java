package com.techworld.admin.product;

import com.techworld.common.entity.Product;
import com.techworld.common.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    public Product findByName(String name);

    public Long countById(Integer id);

    @Query("select p from ProductImage p where p.product.id = ?1")
    public List<ProductImage> findExtraImageByProduct(Integer id);

    @Query("select count(*) from ProductImage p where p.product.id = ?1")
    public Long countImageExtraById(Integer id);

    @Query("update Product  p set p.enabled = ?2 where p.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);

    @Query("update Product p set p.averageRating = coalesce(cast((select avg(r.rating) from Review r where r.product.id = ?1) as float), 0), p.reviewCount = (select count(r.id) from Review r where r.product.id = ?1) where p.id = ?1")
    @Modifying
    public void updateReviewCountAndAverageRating(Integer productId);
}
