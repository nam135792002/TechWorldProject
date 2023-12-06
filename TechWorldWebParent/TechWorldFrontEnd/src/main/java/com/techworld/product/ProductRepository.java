package com.techworld.product;

import com.techworld.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.enabled = true AND p.category.id = ?1")
    List<Product> findProductByCategory(Integer categoryId);

    @Query("select p from Product p where p.createdTime between ?1 and ?2")
    List<Product> findProductByDate(Date startDate, Date endDate);

    @Query("select p from Product p where p.discountPercent between ?1 and ?2")
    List<Product> findProductByDiscountPercent(float start, float end);

    public Product findByAlias(String alias);

    @Query("select p from Product  p where p.enabled = true and concat(p.name,' ',p.category.name,' ',p.brand.name) like %?1% ")
    public List<Product> search(String keyword);

    @Query("SELECT p FROM Product p WHERE p.enabled = true AND p.category.name = ?1")
    List<Product> findProductByCategoryName(String name);

    @Query("SELECT p FROM Product p WHERE p.enabled = true AND p.category.name = ?1")
    List<Product> findProductByCategoryName(String name, Sort sort);

    @Query("select p from Product p where p.enabled = true and p.category.name = ?1 and p.brand.id = ?2")
    List<Product> sortProductByCategoryAndBrand(String categoryName, Integer idBrand, Sort sort);

    @Query("select p from Product p where p.enabled = true and p.category.name = ?1 and p.brand.id = ?2")
    List<Product> sortProductByCategoryAndBrand(String categoryName, Integer idBrand);

    @Query("update Product p set p.averageRating = coalesce(cast((select avg(r.rating) from Review r where r.product.id = ?1) as float), 0), p.reviewCount = (select count(r.id) from Review r where r.product.id = ?1) where p.id = ?1")
    @Modifying
    public void updateReviewCountAndAverageRating(Integer productId);
}
