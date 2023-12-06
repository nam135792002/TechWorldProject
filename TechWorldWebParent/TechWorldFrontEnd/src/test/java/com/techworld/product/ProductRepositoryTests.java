package com.techworld.product;

import com.techworld.common.entity.Category;
import com.techworld.common.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.data.domain.Pageable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProductRepositoryTests {
    @Autowired ProductRepository productRepository;

    @Test
    public void testFindProductByCategory() {
        List<Product> product = productRepository.findProductByCategory(1);
        System.out.println(product.get(1));

        Assertions.assertThat(product).hasSizeGreaterThan(0);
    }

    @Test
    public void testFindProductByDate(){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date startDate = calendar.getTime();
        System.out.println(startDate);

        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);

        Date endDate = calendar.getTime();
        System.out.println(endDate);

        List<Product> listProducts = productRepository.findProductByDate(startDate, endDate);

        listProducts.forEach(System.out::println);

        Assertions.assertThat(listProducts).hasSizeGreaterThan(0);
    }

    @Test
    public void testFindProductByDiscountPercent(){
        List<Product> listProducts = productRepository.findProductByDiscountPercent(0.4f,0.5f);
        listProducts.forEach(System.out::println);

        Assertions.assertThat(listProducts).hasSizeGreaterThan(0);
    }

    @Test
    public void testFindSearch(){
        List<Product> listProducts = productRepository.search("ipad");
        listProducts.forEach(System.out::println);
    }
}
