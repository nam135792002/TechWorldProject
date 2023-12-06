package com.techworld.category;

import com.techworld.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category c where c.enabled = true order by c.name ASC")
    public List<Category> findAllEnabled();

    @Query("select c from Category c where c.enabled = true and c.name = ?1")
    public Category findByNameEnabled(String name);
}
