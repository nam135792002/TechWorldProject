package com.techworld.admin.category;

import com.techworld.common.entity.Brand;
import com.techworld.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateRootCategory(){
        Category category = new Category("Laptop");
        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory(){
        Category parent = new Category("Tablet");
        Category savedCategory = categoryRepository.save(parent);

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }
//
    @Test
    public void testGetCategory(){
        Category category = categoryRepository.findById(2).get();
        System.out.println(category.getName());
    }
//
//
    @Test
    public void testListRootCategories(){
        List<Category> rootCategories = categoryRepository.findAll();
        rootCategories.forEach(cat -> System.out.println(cat.getName()));
    }
//
    @Test
    public void testFindByName(){
        String name = "Laptop";
        Category category = categoryRepository.findByName(name);

        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo(name);
    }

    @Test
    public void listBrandByCategory(){
        Category category = categoryRepository.findByName("laptop");
        Set<Brand> brands = category.getBrands();
        brands.forEach(System.out::println);
    }
}
